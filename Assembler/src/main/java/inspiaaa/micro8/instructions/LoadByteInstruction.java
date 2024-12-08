package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.ErrorReporter;

public class LoadByteInstruction extends ProgramInstruction {
    private final Expression register;
    private final Expression offset;
    private final Expression base;

    public LoadByteInstruction(Expression register, Expression offset, Expression base, int line) {
        super(line);
        this.register = register;
        this.offset = offset;
        this.base = base;
    }

    @Override
    public void validate(SymbolTable symtable, TypeChecker typeChecker, ErrorReporter er) {
        super.validate(symtable, typeChecker, er);

        typeChecker.expect(ParameterType.REGISTER, register);
        typeChecker.expect(ParameterType.IMMEDIATE, offset);
        typeChecker.expect(ParameterType.REGISTER, base);
    }

    @Override
    public void compile(Memory memory, SymbolTable symtable, ErrorReporter er) {
        memory.write(address, line,
                Memory.toBits(0, 0, 1, 0, 0),
                Memory.integerToBits(offset.getValue(symtable), 5),
                Memory.integerToBits(base.getValue(symtable), 3),
                Memory.integerToBits(register.getValue(symtable), 3));
    }
}
