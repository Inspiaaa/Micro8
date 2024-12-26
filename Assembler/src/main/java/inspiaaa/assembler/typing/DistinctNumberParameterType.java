package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.DistinctNumberExpr;
import inspiaaa.assembler.expressions.Expr;

public class DistinctNumberParameterType implements ParameterType {
    private final String distinctType;

    public DistinctNumberParameterType(String type) {
        this.distinctType = type;
    }

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
                    "Expected " + distinctType + " but found: " + value,
                    value.getLocation());
        }

        value = value.unwrap();

        if (value instanceof DistinctNumberExpr distinctInteger && !distinctInteger.getType().equals(distinctType)) {
            errorReporter.reportWarning(
                    "Expected " + distinctType + " but found: " + value,
                    value.getLocation());
        }
    }
}
