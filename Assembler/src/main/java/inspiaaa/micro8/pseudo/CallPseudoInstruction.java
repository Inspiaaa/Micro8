package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Lowerable;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

import java.util.List;

public class CallPseudoInstruction extends Instruction implements Lowerable {
    public CallPseudoInstruction(String mnemonic) {
        super(mnemonic, Parameter.LABEL);
    }

    @Override
    public List<InstructionCall> lower(InstructionCall instruction) {
        Expr rd = new SymbolExpr("ra", symtable, instruction.getLocation());
        Expr label = instruction.getArguments().get(0);
        InstructionCall jal = new InstructionCall("jal", List.of(rd, label), instruction.getLocation());
        return List.of(jal);
    }
}
