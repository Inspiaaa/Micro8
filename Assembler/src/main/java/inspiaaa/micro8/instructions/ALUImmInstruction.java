package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;

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
}
