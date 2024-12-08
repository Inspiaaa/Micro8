package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.ErrorReporter;

public class ALUInstruction extends ProgramInstruction {
    private final int operation;

    private final Expression rd;
    private final Expression rs1;
    private final Expression rs2;

    public ALUInstruction(int operation, Expression rd, Expression rs1, Expression rs2, int line) {
        super(line);
        this.operation = operation;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    @Override
    public void validate(SymbolTable symtable, TypeChecker typeChecker, ErrorReporter er) {
        super.validate(symtable, typeChecker, er);

        typeChecker.expect(ParameterType.REGISTER, rd);
        typeChecker.expect(ParameterType.REGISTER, rs1);
        typeChecker.expect(ParameterType.REGISTER, rs2);
    }

    @Override
    public void compile(Memory memory, SymbolTable symtable, ErrorReporter er) {
        memory.write(address, line,
                Memory.toBits(0, 0, 0, 1),
                Memory.integerToBits(operation, 3),
                Memory.integerToBits(rs2.getValue(symtable), 3),
                Memory.integerToBits(rs1.getValue(symtable), 3),
                Memory.integerToBits(rd.getValue(symtable), 3));
    }
}
