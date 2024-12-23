package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;

public class LoadImmediateInstruction extends ProgramInstruction {
    public LoadImmediateInstruction(String mnemonic) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.IMMEDIATE);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rd = instruction.getArguments().get(0);
        Expr immediate = instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 0, 0, 1),
                memory.integerToBits(immediate, 8),
                memory.integerToBits(rd, 3, false));
    }
}
