package inspiaaa.assembler;

import inspiaaa.assembler.directives.LabelDirective;
import inspiaaa.assembler.parser.*;

import java.util.*;

public class Assembler {
    private final String code;
    private final ArchitectureInformation arch;
    private final ErrorReporter errorReporter;
    private final SymbolTable symtable;

    public Assembler(String code, ArchitectureInformation architectureInformation) {
        this.code = code;
        this.arch = architectureInformation;
        this.errorReporter = new ErrorReporter(code, 3, 1);
        this.symtable = new SymbolTable(errorReporter);
    }

    /*
    Process:
    - Scan tokens.
    - Parse to labels and instruction calls.
    - Convert labels to LabelDirectives and resolve instruction calls to actual Instruction objects.
    - Perform static analysis on instruction arguments (check against expected parameter type).
    - Lower instructions.
    - Assign addresses.
    - Preprocess (in parallel to above, but after each assignAddress call)
    - Compile.
    */

    private void assemble(String code, ErrorReporter errorReporter) {
        List<List<Token>> tokensByLine = Lexer.scan(code, errorReporter);

        List<Instruction> instructions = new ArrayList<Instruction>();

        var parser = new Parser(errorReporter);
        for (List<Token> line : tokensByLine) {
            instructions.add(parseLine(parser, line));
        }

        // TODO: Static analysis

        instructions = lowerInstructions(instructions);

        var addressContext = new AddressContext(arch.getDataCellBitWidth(), arch.getInstructionCellBitWidth());

        for (Instruction instruction : instructions) {
            instruction.assignAddress(addressContext, symtable, errorReporter);
            instruction.preprocess(symtable);
        }

        // TODO: Compile instructions
    }

    private Instruction parseLine(Parser parser, List<Token> line) {
        String label = parser.parseLabelIfPossible(line);
        int lineNumber = line.get(0).getLine();

        if (label != null) {
            return new LabelDirective(label, lineNumber);
        }

        InstructionCallData instruction = parser.parseInstruction(line);
        return resolveInstructionCall(instruction);
    }

    private Instruction resolveInstructionCall(InstructionCallData data) {
        // TODO
        return null;
    }

    private List<Instruction> lowerInstructions(List<Instruction> instructions) {
        var newInstructions = new ArrayList<Instruction>();
        var instructionsToLower = new LinkedList<Instruction>(instructions);

        while (!instructionsToLower.isEmpty()) {
            Instruction instruction = instructionsToLower.removeFirst();

            List<Instruction> lowered = instruction.lower();

            if (lowered == null) {
                newInstructions.add(instruction);
                continue;
            }

            Collections.reverse(lowered);
            for (Instruction newInstruction : lowered) {
                instructionsToLower.addFirst(newInstruction);
            }
        }

        return newInstructions;
    }

    public SymbolTable getSymtable() {
        return symtable;
    }

    public void addConstant(String name, SymbolType type, int value, String... synonyms) {
        symtable.declareBuiltinSymbol(new Symbol(name, type, value));

        for (String synonym : synonyms) {
            symtable.declareSynonym(name, synonym);
        }
    }

    public void addInstruction(String name) {
        // TODO: Params, impl
        symtable.declareBuiltinSymbol(new Symbol(name, SymbolType.INSTRUCTION, false));
    }

    public void addDirective(String name) {
        // TODO: Params, impl
        symtable.declareBuiltinSymbol(new Symbol(name, SymbolType.DIRECTIVE, false));
    }
}
