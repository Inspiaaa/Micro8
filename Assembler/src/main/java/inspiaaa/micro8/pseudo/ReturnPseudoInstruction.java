package inspiaaa.micro8.pseudo;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Lowerable;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

import java.util.List;

public class ReturnPseudoInstruction extends Instruction implements Lowerable {
    public ReturnPseudoInstruction(String mnemonic) {
        super(mnemonic);
    }

    @Override
    public List<InstructionCall> lower(InstructionCall instruction) {
        Expr rs = new SymbolExpr("ra", symtable, instruction.getLocation());
        InstructionCall jr = new InstructionCall("jr", List.of(rs), instruction.getLocation());
        return List.of(jr);
    }
}
