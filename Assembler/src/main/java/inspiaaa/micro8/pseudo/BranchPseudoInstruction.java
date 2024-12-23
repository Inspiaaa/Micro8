package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Lowerable;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;

import java.util.List;

public class BranchPseudoInstruction extends Instruction implements Lowerable {
    private final String actualMnemonic;

    public BranchPseudoInstruction(String mnemonic, String actualMnemonic) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.REGISTER, ParameterType.LABEL);
        this.actualMnemonic = actualMnemonic;
    }

    @Override
    public List<InstructionCall> lower(InstructionCall instruction) {
        Expr rs1 = instruction.getArguments().get(0);
        Expr rs2 = instruction.getArguments().get(1);
        Expr offset = instruction.getArguments().get(2);

        InstructionCall branch = new InstructionCall(actualMnemonic, List.of(rs2, rs1, offset), instruction.getLocation());
        return List.of(branch);
    }
}
