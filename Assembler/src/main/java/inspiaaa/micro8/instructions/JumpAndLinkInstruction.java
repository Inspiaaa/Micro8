package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class JumpAndLinkInstruction extends ProgramInstruction {
    public JumpAndLinkInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Parameter.LABEL);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rd = instruction.getArguments().get(0);
        Expr absoluteAddress = instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 1),
                memory.toBits(1),
                memory.integerToBits(absoluteAddress, 8, false),
                memory.integerToBits(rd, 3, false));
    }
}
