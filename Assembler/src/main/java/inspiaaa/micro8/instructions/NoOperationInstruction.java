package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.memory.Memory;

public class NoOperationInstruction extends ProgramInstruction {
    public NoOperationInstruction(String mnemonic) {
        super(mnemonic);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.integerToBits(0, 16));
    }
}
