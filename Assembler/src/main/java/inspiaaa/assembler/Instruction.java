package inspiaaa.assembler;

import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.ErrorReporter;

import java.util.List;

public class Instruction {
    // Everything is an instruction:
    // - Labels
    // - Assembler directives
    // - Actual instructions

    protected final String mnemonic;
    protected final ParameterType[] parameters;
    protected final boolean isVariadic;

    // Each instruction can only be associated with one assembler at a time.
    protected SymbolTable symtable;
    protected ErrorReporter errorReporter;

    public Instruction(String mnemonic, boolean isVariadic, ParameterType... parameters) {
        this.mnemonic = mnemonic;
        this.parameters = parameters;
        this.isVariadic = isVariadic;
    }

    public Instruction(String mnemonic, ParameterType... parameters) {
        this(mnemonic, false, parameters);
    }

    // Execution order:
    // 1. Lower.
    // 2. Assign addresses.
    // 3. Preprocess (in parallel to above, but after each assignAddress call).
    // 4. Validate.
    // 5. Compile.

    public void preprocess(InstructionCall instruction) {

    }

    public void assignAddress(InstructionCall instruction, AddressContext context) {
        instruction.setAddress(context.getAddress());
    }

    public List<InstructionCall> lower(InstructionCall instruction) {
        return null;
    }

    public void validate(InstructionCall instruction) {

    }

    public void compile(InstructionCall instruction, Memory memory) {

    }

    public void setSymtable(SymbolTable symtable) {
        this.symtable = symtable;
    }

    public void setErrorReporter(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }
}
