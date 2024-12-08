package inspiaaa.micro8.instructions;

import inspiaaa.assembler.Expression;

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
}
