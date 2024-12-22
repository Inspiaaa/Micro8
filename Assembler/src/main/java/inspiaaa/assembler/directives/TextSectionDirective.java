package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class TextSectionDirective extends Instruction {
    private final String bankId;

    public TextSectionDirective(String bankId) {
        super(".text");
        this.bankId = bankId;
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        context.setBank(bankId);
    }
}
