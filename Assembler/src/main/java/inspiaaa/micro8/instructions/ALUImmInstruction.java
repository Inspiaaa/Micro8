package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class ALUImmInstruction extends ProgramInstruction {
    private final int operation;

    public ALUImmInstruction(String mnemonic, int operation) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Parameter.IMMEDIATE);
        this.operation = operation;
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr register = instruction.getArguments().get(0);
        Expr immediate = instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 1),
                memory.integerToBits(operation, 3),
                memory.integerToBits(immediate, 8),
                memory.integerToBits(register, 3, false));
    }
}
