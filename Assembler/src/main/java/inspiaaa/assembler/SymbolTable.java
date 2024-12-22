package inspiaaa.assembler;

import inspiaaa.assembler.parser.Location;

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
        String name = symbol.getName();

        // Special rule for instruction overloads.
        if (symbol.getType() == SymbolType.INSTRUCTION
                && isSymbolDefined(name)
                && symbolsByName.get(name).getType() == SymbolType.INSTRUCTION) {
            return;
        }

        ensureSymbolNotInTable(symbol.getName());
        symbolsByName.put(symbol.getName(), symbol);
    }

    public void declareBuiltinSynonym(String primaryName, String synonym) {
        ensureSymbolNotInTable(synonym);

        if (!symbolsByName.containsKey(primaryName)) {
            throw new RuntimeException("Primary name '" + primaryName + "' not found in symbol table.");
        }

        symbolsByName.put(synonym, symbolsByName.get(primaryName));
    }

    private void reportErrorOnRedeclaration(String symbolName, Location location) {
        if (symbolsByName.containsKey(symbolName)) {
            errorReporter.reportError("Redeclaration of symbol '" + symbolName + "'.", location);
        }
    }

    public void declareNewOrThrow(Symbol symbol, Location location) {
        reportErrorOnRedeclaration(symbol.getName(), location);
        symbolsByName.put(symbol.getName(), symbol);
    }

    public Symbol getSymbol(String name) {
        return symbolsByName.get(name);
    }

    public Symbol getSymbolOrThrow(String name, Location location) {
        if (!isSymbolDefined(name)) {
            errorReporter.reportError("Symbol '" + name + "' not found.", location);
        }

        return getSymbol(name);
    }

    public boolean isSymbolDefined(String name) {
        return symbolsByName.containsKey(name);
    }
}
