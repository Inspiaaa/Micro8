package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class ExactClassParameterType implements ParameterTypeChecker {
    private final Class classType;
    private final String typeDescription;

    public ExactClassParameterType(Class classType, String typeDescription) {
        this.classType = classType;
        this.typeDescription = typeDescription;
    }

    @Override
    public boolean potentiallyMatches(Expr value) {
        if (value instanceof SymbolExpr symbol) {
            if (!symbol.isSymbolDefined()) {
                return true;
            }
        }

        return matches(value);
    }

    @Override
    public boolean matches(Expr value) {
        if (classType.isInstance(value)) {
            return true;
        }

        return classType.isInstance(value.unwrap());
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected " + typeDescription + " but found " + value,
                    value.getLocation());
        }
    }
}
