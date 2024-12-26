package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;

public class UnalignedDataDirective extends Instruction {
    protected final int wordSize;

    public UnalignedDataDirective(String mnemonic, int wordSize) {
        super(mnemonic, true, Parameter.IMMEDIATE);
        this.wordSize = wordSize;
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        instruction.setAddress(context.getAddress());
        context.reserveBits(instruction.getArguments().size() * wordSize);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        boolean[][] binaryData = instruction.getArguments()
                .stream()
                .map(num -> memory.integerToBits(num, wordSize))
                .toArray(boolean[][]::new);

        memory.write(instruction.getAddress(), instruction.getLocation(), binaryData);
    }
}
