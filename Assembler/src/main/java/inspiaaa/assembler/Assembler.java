package inspiaaa.assembler;

import inspiaaa.assembler.directives.LabelDirective;
import inspiaaa.assembler.directives.SynonymPseudoInstruction;
import inspiaaa.assembler.expressions.*;
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

    public Assembler(String file, String code, MemoryArchitecture memoryArchitecture, boolean provideInternalErrorTrace) {
        this.file = file;
        this.code = code;
        this.memoryArchitecture = memoryArchitecture;

        this.errorReporter = new ErrorReporter(1, 1, provideInternalErrorTrace);
        this.errorReporter.loadFile(file, code);

        this.symtable = new SymbolTable(errorReporter);
        this.instructionOverloadsByMnemonic = new HashMap<>();

        this.memory = new Memory(memoryArchitecture, errorReporter);

        defineInstruction(new LabelDirective());
    }

    /*
    Process:
    - Scan tokens.
    - Parse instruction calls (including labels as special directives).
    - For each instruction:
        - Resolve instruction calls to instruction types.
        - Lower instructions.
        - Assign addresses.
        - Preprocess (update symbol table).
    - Validate (static analysis).
    - Compile.
    */

    public void assemble() {
        // Tokenize.
        List<Token> tokens = Lexer.tokenize(file, code, errorReporter);

        // Parse.
        List<InstructionCall> instructions = Parser.parse(tokens, symtable, errorReporter);

        var addressContext = new AddressContext(memoryArchitecture);

        var instructionsToProcess = new LinkedList<InstructionCall>(instructions);
        instructions = new ArrayList<>();

        while (!instructionsToProcess.isEmpty()) {
            InstructionCall call = instructionsToProcess.removeFirst();

            // Resolve.
            resolveInstructionCall(call);

            // Lower.
            if (call.getInstructionDefinition() instanceof Lowerable lowerableInstruction) {
                List<InstructionCall> lowered = lowerableInstruction.lower(call);
                instructionsToProcess.addAll(0, lowered);
                continue;
            }

            // Assign addresses.
            call.getInstructionDefinition().assignAddress(call, addressContext);

            // Preprocess.
            call.getInstructionDefinition().preprocess(call);

            instructions.add(call);
        }

        // Static analysis.
        for (InstructionCall instruction : instructions) {
            instruction.getInstructionDefinition().validate(instruction);
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

    public void defineConstant(String name, long value) {
        defineConstant(name, new NumberExpr(value, null));
    }

    public void defineConstant(String name, String type, long value) {
        defineConstant(name, new DistinctIntegerExpr(type, value, null));
    }

    public void defineConstant(String name, String value) {
        defineConstant(name, new StringExpr(value, null));
    }

    public void defineConstant(String name, Expr value) {
        symtable.declareBuiltinSymbol(new Symbol(name, value));
    }

    public void defineConstantSynonym(String name, String synonym) {
        symtable.declareBuiltinSynonym(name, synonym);
    }

    public void defineInstruction(Instruction instruction, String... synonyms) {
        String mnemonic = instruction.getMnemonic();

        if (!instructionOverloadsByMnemonic.containsKey(mnemonic)) {
            instructionOverloadsByMnemonic.put(mnemonic, new ArrayList<>());
        }

        instruction.setSymtable(symtable);
        instruction.setErrorReporter(errorReporter);

        instructionOverloadsByMnemonic.get(mnemonic).add(instruction);
        symtable.declareBuiltinSymbol(new Symbol(mnemonic, new InstructionReferenceExpr(mnemonic)));

        for (String synonym : synonyms) {
            if (!instructionOverloadsByMnemonic.containsKey(synonym)) {
                instructionOverloadsByMnemonic.put(synonym, new ArrayList<>());
            }

            instructionOverloadsByMnemonic.get(synonym).add(
                    new SynonymPseudoInstruction(synonym, instruction));
        }
    }

    public SymbolTable getSymtable() {
        return symtable;
    }

    public Memory getMemory() {
        return memory;
    }
}
