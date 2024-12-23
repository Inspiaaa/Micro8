package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.instructions.LoadImmediateInstruction;

public class LoadAddressPseudoInstruction extends Instruction {
    private static final LoadImmediateInstruction loadImmediateInstruction = new LoadImmediateInstruction("");

    public LoadAddressPseudoInstruction(String mnemonic) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.LABEL);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        loadImmediateInstruction.compile(instruction, memory);
    }
}
