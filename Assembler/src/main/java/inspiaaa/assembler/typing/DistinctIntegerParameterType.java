package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.DistinctIntegerExpr;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class DistinctIntegerParameterType implements ParameterType {
    private final String distinctType;

    public DistinctIntegerParameterType(String type) {
        this.distinctType = type;
    }

    @Override
    public boolean potentiallyMatches(Expr value) {
        if (value instanceof DistinctIntegerExpr distinct) {
            return distinct.getType().equals(distinctType);
        }

        if (value instanceof SymbolExpr symbol) {
            if (!symbol.isSymbolDefined()) {
                return true;
            }

            return symbol.unwrap() instanceof DistinctIntegerExpr distinct
                && distinct.getType().equals(distinctType);
        }

        return false;
    }

    @Override
    public boolean matches(Expr value) {
        return value.unwrap() instanceof DistinctIntegerExpr distinct
            && distinct.getType().equals(distinctType);
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected " + distinctType + " but found: " + value,
                    value.getLocation());
        }
    }

    @Override
    public String toString() {
        return distinctType;
    }
}
