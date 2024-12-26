package inspiaaa.assembler.typing;

import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class ArgumentTypeUtil {
    public static boolean isPotentiallyNumeric(Expr value) {
        if (value instanceof SymbolExpr symbol) {
            return !symbol.isSymbolDefined() || symbol.isNumeric();
        }

        return value.isNumeric();
    }
}
