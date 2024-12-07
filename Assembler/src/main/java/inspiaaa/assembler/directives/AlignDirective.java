package inspiaaa.assembler.directives;

import inspiaaa.assembler.AddressContext;
import inspiaaa.assembler.Expression;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class AlignDirective extends Instruction {
    private final Expression alignmentExpression;

    public AlignDirective(Expression alignment) {
        this.alignmentExpression = alignment;
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        int alignment = alignmentExpression.getValue(symtable);

        // Align the next instruction to a multiple of the alignment parameter.
        context.setAddress((context.getAddress() + alignment - 1) / alignment * alignment);
    }
}
