package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;

public class MemoryBankDirective extends Instruction {
    private final String bankId;

    public MemoryBankDirective(String mnemonic, String bankId) {
        super(mnemonic);
        this.bankId = bankId;
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        context.setBank(bankId);
    }
}
