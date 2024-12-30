package inspiaaa.micro8.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.directives.UnalignedDataDirective;
import inspiaaa.micro8.StaticAnalysis;

public class CheckedUnalignedDataDirective extends UnalignedDataDirective {
    public CheckedUnalignedDataDirective(String mnemonic, int wordSize) {
        super(mnemonic, wordSize);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);
        StaticAnalysis.ensureIsInDataBank(instruction, errorReporter);
    }
}
