package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;

public class NumericParameterType implements ArgumentTypeChecker {
    @Override
    public boolean potentiallyMatches(Expr value) {
        return ArgumentTypeUtil.isPotentiallyNumeric(value);
    }

    @Override
    public boolean matches(Expr value) {
        return value.isNumeric();
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected numeric type, but found: " + value,
                    value.getLocation());
        }
    }
}
