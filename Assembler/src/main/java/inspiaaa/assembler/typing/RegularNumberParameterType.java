package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.NumberExpr;

// Accepts any numeric value, but reports a warning if it is not a NumberExpr.
public class RegularNumberParameterType implements ParameterType {
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
                    "Expected a regular number, but received: " + value,
                    value.getLocation());
        }
    }

    @Override
    public String toString() {
        return "number";
    }
}
