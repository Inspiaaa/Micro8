package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.Micro8Assembler;

public class ALUInstruction extends ProgramInstruction {
    private final int operation;

    public ALUInstruction(String mnemonic, int operation) {
        super(
                mnemonic,
                Micro8Assembler.REGISTER_TYPE, Micro8Assembler.REGISTER_TYPE, Micro8Assembler.REGISTER_TYPE);
        this.operation = operation;
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr rd = instruction.getArguments().get(0);
        Expr rs1 = instruction.getArguments().get(1);
        Expr rs2 = instruction.getArguments().get(2);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 0),
                memory.integerToBits(operation, 3),
                memory.integerToBits(rs2, 3, false),
                memory.integerToBits(rs1, 3, false),
                memory.integerToBits(rd, 3, false));
    }
}
