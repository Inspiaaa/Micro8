package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;

public class AnyParameterType implements ParameterType {
    @Override
    public boolean potentiallyMatches(Expr value) {
        return true;
    }

    @Override
    public boolean matches(Expr value) {
        return true;
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        
    }

    @Override
    public String toString() {
        return "any";
    }
}
