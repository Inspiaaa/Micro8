package inspiaaa.assembler;

import inspiaaa.assembler.parser.ErrorReporter;

import java.util.HashMap;

public class SymbolTable {
    private final HashMap<String, Symbol> symbolsByName = new HashMap<>();
    private final ErrorReporter errorReporter;

    public SymbolTable(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    private void ensureSymbolNotInTable(String name) {
        if (symbolsByName.containsKey(name)) {
            throw new RuntimeException("Redeclaration of symbol '" + name + "' is disallowed.");
        }
    }

    public void declareBuiltinSymbol(Symbol symbol) {
        ensureSymbolNotInTable(symbol.getName());
        symbolsByName.put(symbol.getName(), symbol);
    }

    private void reportErrorOnRedeclaration(String symbolName, int line) {
        if (symbolsByName.containsKey(symbolName)) {
            errorReporter.reportError("Redeclaration of symbol '" + symbolName + "'.", line);
        }
    }

    public void declare(Symbol symbol, int line) {
        reportErrorOnRedeclaration(symbol.getName(), line);
        symbolsByName.put(symbol.getName(), symbol);
    }

    public void declareSynonym(String primaryName, String synonym) {
        ensureSymbolNotInTable(synonym);

        if (!symbolsByName.containsKey(primaryName)) {
            throw new RuntimeException("Primary name '" + primaryName + "' not found in symbol table.");
        }

        symbolsByName.put(synonym, symbolsByName.get(primaryName));
    }

    public Symbol getSymbol(String name, int line) {
        if (!symbolsByName.containsKey(name)) {
            errorReporter.reportError("Symbol '" + name + "' not found.", line);
        }

        return symbolsByName.get(name);
    }

    public int getNumericValue(String name, int line) {
        Symbol symbol = getSymbol(name, line);

        if (!symbol.hasNumericValue()) {
            errorReporter.reportError(
                "'" + name + "' (" + symbol.getType() + ") does not have a numeric representation.",
                line);
        }

        return symbol.getValue();
    }

    public boolean isSymbolDefined(String name) {
        return symbolsByName.containsKey(name);
    }
}
