package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class MoveInstruction extends ProgramInstruction {
    public MoveInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Micro8Assembler.REGISTER_TYPE);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rd = instruction.getArguments().get(0);
        Expr rs = instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 0, 0, 0, 0, 1),
                memory.zeroBits(3),
                memory.integerToBits(rs, 3, false),
                memory.integerToBits(rd, 3, false));
    }
}
