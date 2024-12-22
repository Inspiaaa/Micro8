package inspiaaa.assembler;

import inspiaaa.assembler.expressions.*;
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

    public void validate(InstructionCall instruction, TypeChecker typeChecker) {
        List<Expr> arguments = instruction.getArguments();

        for (int i = 0; i < arguments.size(); i++) {
            Expr arg = arguments.get(i);
            // For variadic functions the last parameter is repeated multiple times.
            ParameterType param = parameters[Math.min(parameters.length, i)];

            typeChecker.checkArgumentType(arg, param);
        }
    }

    public void compile(InstructionCall instruction, Memory memory) {

    }

    public void setSymtable(SymbolTable symtable) {
        this.symtable = symtable;
    }

    public void setErrorReporter(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    public boolean matchesSignature(InstructionCall instruction) {
        List<Expr> arguments = instruction.getArguments();

        if (!isVariadic && arguments.size() != parameters.length) {
            return false;
        }
        else if (isVariadic && arguments.size() < parameters.length - 1) {
            return false;
        }

        for (int i = 0; i < arguments.size(); i++) {
            Expr arg = arguments.get(i);
            // For variadic functions the last parameter is repeated multiple times.
            ParameterType param = parameters[Math.min(parameters.length, i)];

            if (!TypeChecker.argumentMatchesParameterType(arg, param)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (parameters.length == 0) {
            return mnemonic;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(mnemonic);

        sb.append(" ");
        sb.append(parameters[0]);

        for (int i = 1; i < parameters.length; i++) {
            sb.append(", ");
            sb.append(parameters[i]);
        }

        return sb.toString();
    }
}
