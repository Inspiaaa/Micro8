package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;
import inspiaaa.micro8.instructions.LoadImmediateInstruction;
import inspiaaa.micro8.instructions.ProgramInstruction;

public class LoadAddressPseudoInstruction extends ProgramInstruction {
    private static final LoadImmediateInstruction loadImmediateInstruction = new LoadImmediateInstruction("");

    public LoadAddressPseudoInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Parameter.LABEL);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        loadImmediateInstruction.compile(instruction, memory);
    }
}
