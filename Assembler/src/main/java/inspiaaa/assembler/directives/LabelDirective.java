package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.Symbol;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.SymbolType;

public class LabelDirective extends Instruction {
    private final String label;

    public LabelDirective(String label) {
        this.label = label;
    }

    @Override
    public void preprocess(SymbolTable symtable) {
        symtable.declare(new Symbol(label, SymbolType.LABEL, address), line);
    }
}
