package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.RelativeAddressExpr;

public class RelativeAddressParameterType implements ParameterType {
    private final ImmediateParameterType offsetType;
    private final DistinctNumberParameterType baseType;

    public RelativeAddressParameterType(String registerType) {
        this.offsetType = new ImmediateParameterType();
        this.baseType = new DistinctNumberParameterType(registerType);
    }

    @Override
    public boolean matches(Expr value) {
        return value instanceof RelativeAddressExpr;
    }

    @Override
    public boolean potentiallyMatches(Expr value) {
        return matches(value);
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected register-relative address but found " + value,
                    value.getLocation());
        }

        RelativeAddressExpr relativeAddress = (RelativeAddressExpr)value;

        offsetType.check(relativeAddress.getOffset(), errorReporter);
        baseType.check(relativeAddress.getBase(), errorReporter);
    }

    @Override
    public String toString() {
        return "offset(base)";
    }
}
