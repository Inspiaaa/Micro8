package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;

public class ALUImmInstruction extends ProgramInstruction {
    private final int operation;

    public ALUImmInstruction(String mnemonic, int operation) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.IMMEDIATE);
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
