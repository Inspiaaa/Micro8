package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.ErrorReporter;

public class ALUImmInstruction extends ProgramInstruction {
    private final int operation;
    private final Expression register;
    private final Expression immediate;

    public ALUImmInstruction(int operation, Expression register, Expression immediate, int line) {
        super(line);
        this.operation = operation;
        this.register = register;
        this.immediate = immediate;
    }

    @Override
    public void validate(SymbolTable symtable, TypeChecker typeChecker, ErrorReporter er) {
        super.validate(symtable, typeChecker, er);

        typeChecker.expect(ParameterType.REGISTER, register);
        typeChecker.expect(ParameterType.IMMEDIATE, immediate);
    }

    @Override
    public void compile(Memory memory, SymbolTable symtable, ErrorReporter er) {
        memory.write(address, line,
                Memory.toBits(0, 1),
                Memory.integerToBits(operation, 3),
                Memory.integerToBits(immediate.getValue(symtable), 8),
                Memory.integerToBits(register.getValue(symtable), 3));
    }
}
