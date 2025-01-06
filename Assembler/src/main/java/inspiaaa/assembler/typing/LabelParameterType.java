package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.LabelExpr;

// Accepts any numeric type but reports a warning if the value is not a label.
public class LabelParameterType implements ParameterType {
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
                    "Expected label (numeric type), but received: " + value,
                    value.getLocation());
        }

        value = value.unwrap();

        if (!(value instanceof LabelExpr)) {
            errorReporter.reportWarning(
                    "Expected label, but received direct offset: " + value,
                    value.getLocation());
        }
    }

    @Override
    public String toString() {
        return "label";
    }
}
