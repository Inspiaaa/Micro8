package inspiaaa.assembler.directives;

import inspiaaa.assembler.AddressContext;
import inspiaaa.assembler.Expression;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class OrgDirective extends Instruction {
    private final Expression targetAddressExpression;

    public OrgDirective(Expression targetAddress) {
        this.targetAddressExpression = targetAddress;
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        int targetAddress = targetAddressExpression.getValue(symtable);
        context.setAddress(targetAddress);
    }
}
