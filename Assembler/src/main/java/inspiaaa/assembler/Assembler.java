package inspiaaa.assembler;

import inspiaaa.assembler.directives.LabelDirective;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.*;

import java.util.*;

public class Assembler {
    private final String code;

    private final ArchitectureInformation arch;

    private final ErrorReporter errorReporter;
    private final SymbolTable symtable;
    private final HashMap<String, ArrayList<InstructionDefinition>> instructionOverloadsByName;

    private final Memory memory;

    public Assembler(String code, ArchitectureInformation architectureInformation) {
        this.code = code;
        this.arch = architectureInformation;
        this.errorReporter = new ErrorReporter(code, 3, 1);
        this.symtable = new SymbolTable(errorReporter);
        this.instructionOverloadsByName = new HashMap<>();

        this.memory = new Memory(
                arch.getDataMemorySize(),
                arch.getInstructionMemorySize(),
                arch.getDataCellBitWidth(),
                arch.getInstructionCellBitWidth(),
                errorReporter
        );
    }

    /*
    Process:
    - Scan tokens.
    - Parse to labels and instruction calls.
    - Convert labels to LabelDirectives and resolve instruction calls to actual Instruction objects.
    - Lower instructions.
    - Assign addresses.
    - Preprocess (in parallel to above, but after each assignAddress call).
    - Validate (static analysis).
    - Compile.
    */

    public void assemble() {
        // Lex.
        List<List<Token>> tokensByLine = Lexer.scan(code, errorReporter);

        // Parse.
        List<Instruction> instructions = new ArrayList<Instruction>();
        var parser = new Parser(errorReporter);
        for (List<Token> line : tokensByLine) {
            instructions.add(parseLine(parser, line));
        }

        // Lower instructions.
        instructions = lowerInstructions(instructions);

        // Allocate addresses.
        var addressContext = new AddressContext(arch.getDataCellBitWidth(), arch.getInstructionCellBitWidth());

        for (Instruction instruction : instructions) {
            instruction.assignAddress(addressContext, symtable, errorReporter);
            instruction.preprocess(symtable);
        }

        // Static analysis.
        TypeChecker typeChecker = new TypeChecker(symtable, errorReporter);
        for (Instruction instruction : instructions) {
            instruction.validate(symtable, typeChecker, errorReporter);
        }

        // Compile.
        for (Instruction instruction : instructions) {
            instruction.compile(memory, symtable, errorReporter);
        }
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

    private Instruction resolveInstructionCall(InstructionCallData call) {
        String name = call.getName();

        if (!instructionOverloadsByName.containsKey(name)) {
            errorReporter.reportError("Instruction '" + name + "' not found.", call.getLine());
        }

        List<InstructionDefinition> overloads = instructionOverloadsByName.get(name);

        for (InstructionDefinition overload : overloads) {
            Instruction instruction = overload.tryConvert(call);
            if (instruction != null) {
                return instruction;
            }
        }

        reportNoSuitableOverload(overloads, call.getLine());

        return null;
    }

    private void reportNoSuitableOverload(List<InstructionDefinition> overloads, int line) {
        var message = new StringBuilder();

        message.append("No suitable instruction overload found. Candidates:");

        for (InstructionDefinition overload : overloads) {
            message.append("\n- ");
            message.append(overload.toString());
        }

        errorReporter.reportError(message.toString(), line);
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

    public void defineConstant(String name, SymbolType type, int value, String... synonyms) {
        symtable.declareBuiltinSymbol(new Symbol(name, type, value));

        for (String synonym : synonyms) {
            symtable.declareSynonym(name, synonym);
        }
    }

    public void defineInstruction(String name, InstructionFactory factory, ParameterType... parameters) {
        addInstructionDefinition(new InstructionDefinition(name, parameters, factory));
        symtable.declareBuiltinSymbol(new Symbol(name, SymbolType.INSTRUCTION, false));
    }

    private void addInstructionDefinition(InstructionDefinition def) {
        String name = def.getName();
        if (!instructionOverloadsByName.containsKey(name)) {
            instructionOverloadsByName.put(name, new ArrayList<InstructionDefinition>());
        }

        ArrayList<InstructionDefinition> overloads = instructionOverloadsByName.get(name);

        for (InstructionDefinition overload : overloads) {
            if (overload.getParameterCount() == def.getParameterCount()) {
                throw new RuntimeException("Instruction overload for '" + name + "' with "
                        + def.getParameterCount() + " parameter(s) already exists.");
            }
        }

        overloads.add(def);
    }

    public SymbolTable getSymtable() {
        return symtable;
    }

    public Memory getMemory() {
        return memory;
    }
}
