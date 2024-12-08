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

        typeChecker.expect(register, ParameterType.REGISTER);
        typeChecker.expect(immediate, ParameterType.IMMEDIATE);
    }

    @Override
    public void compile(Memory memory, SymbolTable symtable, ErrorReporter er) {
        memory.write(
            address,
            line,
            Memory.toBits(1, 0),
            Memory.integerToBits(operation, 3)
        );
    }
}
