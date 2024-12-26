package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.NumberExpr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class ImmediateParameterType implements ParameterTypeChecker {
    @Override
    public boolean potentiallyMatches(Expr value) {
        return TypeUtil.isPotentiallyNumeric(value);
    }

    @Override
    public boolean matches(Expr value) {
        return value.isNumeric();
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected a number as argument, but received " + value + ".",
                    value.getLocation());
        }

        value = value.unwrap();

        if (!(value instanceof NumberExpr)) {
            errorReporter.reportWarning(
                    "Expected and immediate, but received: " + value,
                    value.getLocation());
        }
    }
}
