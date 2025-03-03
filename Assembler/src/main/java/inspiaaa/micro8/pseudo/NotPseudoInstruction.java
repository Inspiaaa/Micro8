package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Lowerable;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.NumberExpr;
import inspiaaa.micro8.Micro8Assembler;

import java.util.List;

public class NotPseudoInstruction extends Instruction implements Lowerable {
    public NotPseudoInstruction(String mnemonic) {
        super(mnemonic, Micro8Assembler.REGISTER_TYPE);
    }

    @Override
    public List<InstructionCall> lower(InstructionCall instruction) {
        Expr rd = instruction.getArguments().get(0);
        Expr ones = new NumberExpr(0b11111111, instruction.getLocation());
        InstructionCall xori = new InstructionCall("xori", List.of(rd, ones), instruction.getLocation());
        return List.of(xori);
    }
}
