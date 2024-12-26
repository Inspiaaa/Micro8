package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

import java.util.Arrays;

public class BranchInstruction extends ProgramInstruction {
    private final int operation;

    public BranchInstruction(String mnemonic, int operation) {
        super(
                mnemonic,
                Micro8Assembler.REGISTER_TYPE, Micro8Assembler.REGISTER_TYPE, Parameter.LABEL);
        this.operation = operation;
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rs1 = instruction.getArguments().get(0);
        Expr rs2 = instruction.getArguments().get(1);
        Expr offset = instruction.getArguments().get(2);

        int relativeAddress = (int)offset.getRelativeAddress(instruction.getAddress().getAddress());

        boolean[] offsetBits = memory.integerToBits(relativeAddress, 7, true, offset.getLocation());
        boolean[] lowBits = Arrays.copyOfRange(offsetBits, 0, 4);
        boolean[] highBits = Arrays.copyOfRange(offsetBits, 4, 7);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(1),
                memory.integerToBits(operation, 2),
                lowBits,
                memory.integerToBits(rs2, 3, false),
                memory.integerToBits(rs1, 3, false),
                highBits);
    }
}
