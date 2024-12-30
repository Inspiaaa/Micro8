package inspiaaa.micro8.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.directives.AlignedDataDirective;
import inspiaaa.micro8.StaticAnalysis;

public class CheckedAlignedDataDirective extends AlignedDataDirective {
    public CheckedAlignedDataDirective(String mnemonic, int wordSize) {
        super(mnemonic, wordSize);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);
        StaticAnalysis.ensureIsInDataBank(instruction, errorReporter);
    }
}
