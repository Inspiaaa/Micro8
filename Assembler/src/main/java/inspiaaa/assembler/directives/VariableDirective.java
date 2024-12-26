package inspiaaa.assembler.directives;

import inspiaaa.assembler.*;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class VariableDirective extends Instruction {
    public VariableDirective(String mnemonic) {
        super(mnemonic, Parameter.SYMBOL, Parameter.ANY);
    }

    @Override
    public void preprocess(InstructionCall instruction) {
        var arguments = instruction.getArguments();

        SymbolExpr nameExpr = ((SymbolExpr)arguments.get(0));
        String name = nameExpr.getName();
        Expr value = arguments.get(1);

        if (value instanceof SymbolExpr valueSymbol) {
            value = valueSymbol.getSymbol().getValue();
        }

        Symbol symbol = new Symbol(name, value);
        symtable.declareNewOrThrow(symbol, nameExpr.getLocation());
    }
}
