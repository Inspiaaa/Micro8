package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.RelativeAddressExpr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

import java.util.Arrays;

public class StoreByteInstruction extends ProgramInstruction {
    public StoreByteInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Parameter.relativeAddress(Micro8Assembler.REGISTER));
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rs2 = instruction.getArguments().get(0);
        RelativeAddressExpr address = (RelativeAddressExpr) instruction.getArguments().get(1);
        Expr rs1 = address.getBase();
        Expr offset = address.getOffset();

        boolean[] offsetBits = memory.integerToBits(offset, 5, false);
        boolean[] lowBits = Arrays.copyOfRange(offsetBits, 0, 2);
        boolean[] highBits = Arrays.copyOfRange(offsetBits, 2, 5);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 0, 1, 1),
                lowBits,
                memory.integerToBits(rs2, 3, false),
                memory.integerToBits(rs1, 3, false),
                highBits);
    }
}
