package inspiaaa.assembler;

import inspiaaa.assembler.directives.LabelDirective;
import inspiaaa.assembler.expressions.NumberExpr;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryArchitecture;
import inspiaaa.assembler.parser.*;

import java.util.*;

public class Assembler {
    private final String file;
    private final String code;

    private final MemoryArchitecture memoryArchitecture;

    private final ErrorReporter errorReporter;
    private final SymbolTable symtable;
    private final HashMap<String, ArrayList<Instruction>> instructionOverloadsByMnemonic;

    private final Memory memory;

    public Assembler(String file, String code, MemoryArchitecture memoryArchitecture) {
        this.file = file;
        this.code = code;
        this.memoryArchitecture = memoryArchitecture;

        this.errorReporter = new ErrorReporter(3, 1);
        this.errorReporter.loadFile(file, code);

        this.symtable = new SymbolTable(errorReporter);
        this.instructionOverloadsByMnemonic = new HashMap<>();

        this.memory = new Memory(memoryArchitecture, errorReporter);

        defineInstruction(new LabelDirective());
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
        // Tokenize.
        List<Token> tokens = Lexer.tokenize(file, code, errorReporter);

        // Parse.
        List<InstructionCall> instructions = Parser.parse(tokens, symtable, errorReporter);

        // Resolve instructions. (Bind to concrete overload)
        for (InstructionCall call : instructions) {
            resolveInstructionCall(call);
        }

        // Lower instructions.
        instructions = lowerInstructions(instructions);

        // Allocate addresses.
        var addressContext = new AddressContext(memoryArchitecture);

        for (InstructionCall instruction : instructions) {
            Instruction definition = instruction.getInstructionDefinition();
            definition.assignAddress(instruction, addressContext);
            definition.preprocess(instruction);
        }

        // Static analysis.
        TypeChecker typeChecker = new TypeChecker(errorReporter);
        for (InstructionCall instruction : instructions) {
            instruction.getInstructionDefinition().validate(instruction, typeChecker);
        }

        // Compile.
        for (InstructionCall instruction : instructions) {
            instruction.getInstructionDefinition().compile(instruction, memory);
        }
    }

    private void resolveInstructionCall(InstructionCall call) {
        String mnemonic = call.getMnemonic();

        if (!instructionOverloadsByMnemonic.containsKey(mnemonic)) {
            errorReporter.reportError("Instruction '" + mnemonic + "' not found.", call.getLocation());
        }

        List<Instruction> overloads = instructionOverloadsByMnemonic.get(mnemonic);

        for (Instruction overload : overloads) {
            if (overload.matchesSignature(call)) {
                call.setInstructionDefinition(overload);
                return;
            }
        }

        reportNoSuitableOverload(overloads, call.getLocation());
    }

    private void reportNoSuitableOverload(List<Instruction> overloads, Location location) {
        var message = new StringBuilder();

        message.append("No suitable instruction overload found. Candidates:");

        for (Instruction overload : overloads) {
            message.append("\n- ");
            message.append(overload.toString());
        }

        errorReporter.reportError(message.toString(), location);
    }

    private List<InstructionCall> lowerInstructions(List<InstructionCall> instructions) {
        var newInstructions = new ArrayList<InstructionCall>();
        var instructionsToLower = new LinkedList<InstructionCall>(instructions);

        while (!instructionsToLower.isEmpty()) {
            InstructionCall instruction = instructionsToLower.removeFirst();

            List<InstructionCall> lowered = instruction.getInstructionDefinition().lower(instruction);

            if (lowered == null) {
                newInstructions.add(instruction);
                continue;
            }

            Collections.reverse(lowered);
            for (InstructionCall newInstruction : lowered) {
                resolveInstructionCall(newInstruction);
                instructionsToLower.addFirst(newInstruction);
            }
        }

        return newInstructions;
    }

    public void defineConstant(String name, SymbolType type, int value, String... synonyms) {
        symtable.declareBuiltinSymbol(new Symbol(name, type, new NumberExpr(value, null)));

        for (String synonym : synonyms) {
            symtable.declareBuiltinSynonym(name, synonym);
        }
    }

    public void defineInstruction(Instruction instruction) {
        String mnemonic = instruction.getMnemonic();

        if (!instructionOverloadsByMnemonic.containsKey(mnemonic)) {
            instructionOverloadsByMnemonic.put(mnemonic, new ArrayList<>());
        }

        instruction.setSymtable(symtable);
        instruction.setErrorReporter(errorReporter);

        instructionOverloadsByMnemonic.get(mnemonic).add(instruction);
        symtable.declareBuiltinSymbol(new Symbol(mnemonic, SymbolType.INSTRUCTION));
    }

    public SymbolTable getSymtable() {
        return symtable;
    }

    public Memory getMemory() {
        return memory;
    }
}
