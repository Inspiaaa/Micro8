package inspiaaa.assembler;

import inspiaaa.assembler.expressions.*;

public class TypeChecker {
    private final ErrorReporter errorReporter;

    public TypeChecker(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    public static boolean argumentMatchesParameterType(Expr arg, ParameterType param) {
        return switch (param) {
            case IMMEDIATE, LABEL, REGISTER -> arg.isNumeric();
            case RELATIVE_ADDRESS -> arg instanceof RelativeAddressExpr;
            case STRING -> arg instanceof StringExpr;
        };
    }

    public void checkArgumentType(Expr arg, ParameterType param) {
        if (!argumentMatchesParameterType(arg, param)) {
            errorReporter.reportError(
                    "Expected " + param + " as argument type, but received " + arg + ".",
                    arg.getLocation());
        }

        if (arg instanceof RelativeAddressExpr relativeAddress) {
            if (!relativeAddress.getOffset().isNumeric()) {
                errorReporter.reportError("Expected a number.", relativeAddress.getOffset().getLocation());
            }
            if (!relativeAddress.getBase().isNumeric()) {
                errorReporter.reportError("Expected a number.", relativeAddress.getBase().getLocation());
            }
            return;
        }

        if (isExpressionSuspicious(arg, param)) {
            errorReporter.reportWarning("Expected a " + param + ", but received " + arg, arg.getLocation());
        }
    }

    private boolean isExpressionSuspicious(Expr expr, ParameterType param) {
        return switch (param) {
            case REGISTER -> !isExpressionNaturalRegister(expr);

            case RELATIVE_ADDRESS -> {
                RelativeAddressExpr relativeAddress = (RelativeAddressExpr)expr;
                yield isExpressionSuspicious(relativeAddress.getOffset(), ParameterType.IMMEDIATE)
                    || isExpressionSuspicious(relativeAddress.getBase(), ParameterType.REGISTER);
            }

            case IMMEDIATE -> !isExpressionNaturalNumber(expr);

            case LABEL -> !isExpressionNaturalLabel(expr);

            default -> false;
        };
    }

    private boolean isExpressionNaturalRegister(Expr expr) {
        if (!(expr instanceof SymbolExpr symbol)) {
            return false;
        }
        return symbol.getSymbol().getType() == SymbolType.REGISTER;
    }

    private boolean isExpressionNaturalNumber(Expr expr) {
        if (expr instanceof SymbolExpr symbol) {
            return symbol.getSymbol().getType() == SymbolType.VARIABLE;
        }
        return expr instanceof NumberExpr || expr instanceof CharExpr;
    }

    private boolean isExpressionNaturalLabel(Expr expr) {
        if (!(expr instanceof SymbolExpr symbol)) {
            return false;
        }
        return symbol.getSymbol().getType() == SymbolType.LABEL;
    }
}
