package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.Symbol;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.SymbolType;

public class LabelDirective extends Instruction {
    public static final String VIRTUAL_MNEMONIC = "$label";

    private final String label;

    public LabelDirective(String label, int line) {
        super(line);
        this.label = label;
    }

    @Override
    public void preprocess(SymbolTable symtable) {
        symtable.declare(new Symbol(label, SymbolType.LABEL, address.getValue()), line);
    }
}
