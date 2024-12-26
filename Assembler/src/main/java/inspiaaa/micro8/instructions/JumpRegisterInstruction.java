package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class JumpRegisterInstruction extends ProgramInstruction {
    public JumpRegisterInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rs = instruction.getArguments().get(0);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 0, 0, 0, 1, 0),
                memory.toBits(0),
                memory.zeroBits(2),
                memory.integerToBits(rs, 3, false),
                memory.zeroBits(3));
    }
}
