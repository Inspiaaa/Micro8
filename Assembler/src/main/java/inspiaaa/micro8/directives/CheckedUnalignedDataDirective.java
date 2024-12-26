package inspiaaa.micro8.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.directives.UnalignedDataDirective;
import inspiaaa.micro8.Micro8Assembler;

public class CheckedUnalignedDataDirective extends UnalignedDataDirective {
    public CheckedUnalignedDataDirective(String mnemonic, int wordSize) {
        super(mnemonic, wordSize);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);

        if (!instruction.getAddress().getBankId().equals(Micro8Assembler.DATA_BANK)) {
            errorReporter.reportWarning(
                    "Writing data to instruction section. Use '.data' to switch to data section.",
                    instruction.getLocation());
        }
    }
}
