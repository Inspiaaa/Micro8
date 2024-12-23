package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;

public class JumpAndLinkRegisterInstruction extends ProgramInstruction {
    public JumpAndLinkRegisterInstruction(String mnemonic) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.REGISTER);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rd = instruction.getArguments().get(0);
        Expr rs = instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 0, 0, 0, 1, 0),
                memory.toBits(1),
                memory.zeroBits(2),
                memory.integerToBits(rs, 3, false),
                memory.integerToBits(rd, 3, false));
    }
}
