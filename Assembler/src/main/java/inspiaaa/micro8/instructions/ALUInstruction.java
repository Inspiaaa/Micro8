package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;

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
}
