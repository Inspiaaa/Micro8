package inspiaaa.assembler;

public class Symbol {
    private final String name;
    private final SymbolType type;
    private final boolean hasNumericValue;
    private int value;

    public Symbol(String name, SymbolType type, boolean hasNumericValue) {
        this.name = name;
        this.type = type;
        this.hasNumericValue = hasNumericValue;
    }

    public Symbol(String name, SymbolType type, int value) {
        this.name = name;
        this.type = type;
        this.hasNumericValue = true;
        this.value = value;
    }

    public SymbolType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public boolean hasNumericValue() {
        return hasNumericValue;
    }
}
