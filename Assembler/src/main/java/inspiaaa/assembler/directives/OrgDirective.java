package inspiaaa.assembler.directives;

import inspiaaa.assembler.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class OrgDirective extends Instruction {
    private final int targetAddress;

    public OrgDirective(int targetAddress) {
        this.targetAddress = targetAddress;
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        context.setAddress(targetAddress);
    }
}
