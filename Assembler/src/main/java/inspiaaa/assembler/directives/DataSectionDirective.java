package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;

public class DataSectionDirective extends Instruction {
    private final String bankId;

    public DataSectionDirective(String bankId) {
        super(".data");
        this.bankId = bankId;
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        context.setBank(bankId);
    }
}
