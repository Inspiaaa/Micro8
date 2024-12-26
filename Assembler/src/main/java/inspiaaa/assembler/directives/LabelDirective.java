package inspiaaa.assembler.directives;

import inspiaaa.assembler.*;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.LabelExpr;
import inspiaaa.assembler.expressions.NumberExpr;
import inspiaaa.assembler.expressions.StringExpr;

public class LabelDirective extends Instruction {
    public static final String VIRTUAL_MNEMONIC = "$label";

    public LabelDirective() {
        super(VIRTUAL_MNEMONIC, Parameter.STRING);
    }

    @Override
    public void preprocess(InstructionCall instruction) {
        StringExpr expression = (StringExpr) instruction.getArguments().get(0);
        String label = expression.getValue();
        Expr labelAddress = new LabelExpr(instruction.getAddress().getAddress(), expression.getLocation());

        symtable.declareNewOrThrow(new Symbol(label, labelAddress), expression.getLocation());
    }
}
