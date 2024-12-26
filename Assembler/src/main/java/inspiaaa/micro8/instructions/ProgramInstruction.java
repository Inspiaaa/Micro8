package inspiaaa.micro8.instructions;

import inspiaaa.assembler.*;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.typing.ArgumentTypeChecker;
import inspiaaa.micro8.Micro8Assembler;

public class ProgramInstruction extends Instruction {
    public ProgramInstruction(String mnemonic, boolean isVariadic, ArgumentTypeChecker... parameters) {
        super(mnemonic, isVariadic, parameters);
    }

    public ProgramInstruction(String mnemonic, ArgumentTypeChecker... parameters) {
        super(mnemonic, parameters);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        instruction.setAddress(context.getAddress());
        context.reserveBits(16);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);

        if (!instruction.getAddress().getBankId().equals(Micro8Assembler.INSTRUCTION_BANK)) {
            errorReporter.reportWarning(
                    "Writing instruction to data section. Use '.text' to switch to instruction section.",
                    instruction.getLocation());
        }
    }
}
