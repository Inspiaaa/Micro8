package inspiaaa.micro8.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.directives.AsciiDirective;
import inspiaaa.micro8.StaticAnalysis;

public class CheckedAsciiDirective extends AsciiDirective {
    public CheckedAsciiDirective(String mnemonic, boolean zeroDelimiter) {
        super(mnemonic, zeroDelimiter);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);
        StaticAnalysis.ensureIsInDataBank(instruction, errorReporter);
    }
}
