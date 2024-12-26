package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.RelativeAddressExpr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class LoadByteInstruction extends ProgramInstruction {
    public LoadByteInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE, Parameter.relativeAddress(Micro8Assembler.REGISTER));
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr register = instruction.getArguments().get(0);
        RelativeAddressExpr relativeAddress = (RelativeAddressExpr) instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 0, 0),
                memory.integerToBits(relativeAddress.getOffset(), 5, false),
                memory.integerToBits(relativeAddress.getBase(), 3, false),
                memory.integerToBits(register, 3, false));
    }
}
