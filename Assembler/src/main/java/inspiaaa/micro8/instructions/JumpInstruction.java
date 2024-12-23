package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;

public class JumpInstruction extends ProgramInstruction {
    public JumpInstruction(String mnemonic) {
        super(mnemonic, ParameterType.LABEL);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr absoluteAddress = instruction.getArguments().get(0);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 1),
                memory.toBits(0),
                memory.integerToBits(absoluteAddress, 8, false),
                memory.zeroBits(3));
    }
}
