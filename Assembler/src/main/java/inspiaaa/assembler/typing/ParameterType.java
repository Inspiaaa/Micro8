package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;

// Used for type checking operand values against parameter types.
public interface ParameterType {
    // Returns true if the given value matches the type or if the value is an unresolved symbol
    // which may match once it is defined.
    boolean potentiallyMatches(Expr value);

    // Returns true if the given value matches the type. Can report an error if the value is
    // an unresolved symbol.
    boolean matches(Expr value);

    // Checks whether the given value matches the parameter type. It should report an error if not.
    // Furthermore, it can report a warning if the value is legal but likely erroneous.
    void check(Expr value, ErrorReporter errorReporter);
}
