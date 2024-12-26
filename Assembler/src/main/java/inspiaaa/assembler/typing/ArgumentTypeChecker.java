package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;

public interface ArgumentTypeChecker {
    boolean potentiallyMatches(Expr value);
    boolean matches(Expr value);
    void check(Expr value, ErrorReporter errorReporter);
}
