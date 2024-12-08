package inspiaaa.assembler;

import inspiaaa.assembler.parser.ErrorReporter;

public class TypeChecker {
    private final SymbolTable symtable;
    private final ErrorReporter errorReporter;

    public TypeChecker(SymbolTable symtable, ErrorReporter errorReporter) {
        this.symtable = symtable;
        this.errorReporter = errorReporter;
    }

    public void expect(Expression expression, ParameterType expectedType) {
        checkExpressionType(expression, expectedType);
    }

    public void checkExpressionType(Expression expression, ParameterType expectedType) {
        if (expression instanceof NumericExpression ne) {
            checkNumericType(ne, expectedType);
        }
        else if (expression instanceof SymbolicExpression se) {
            checkSymbolType(se, expectedType);
        }
    }

    private void checkNumericType(NumericExpression expression, ParameterType expectedType) {
        if (expectedType != ParameterType.IMMEDIATE) {
            errorReporter.reportWarning(
                    "Expected " + expectedType + " as argument, but received immediate.",
                    expression.getLine());
        }
    }

    private void checkSymbolType(SymbolicExpression expression, ParameterType expectedType) {
        Symbol symbol = symtable.getSymbol(expression.getName(), expression.getLine());

        if ((expectedType == ParameterType.LABEL && symbol.getType() != SymbolType.LABEL)
                || (expectedType == ParameterType.IMMEDIATE && symbol.getType() != SymbolType.VARIABLE)
                || (expectedType == ParameterType.REGISTER && symbol.getType() != SymbolType.REGISTER)) {

            errorReporter.reportWarning(
                    "Expected " + expectedType + " as argument, but received " + symbol + ".",
                    expression.getLine());
        }
    }
}
