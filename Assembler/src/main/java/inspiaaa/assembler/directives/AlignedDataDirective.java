package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;

public class AlignedDataDirective extends Instruction {
    protected final int wordSize;

    public AlignedDataDirective(String mnemonic, int wordSize) {
        super(mnemonic, true, Parameter.NUMERIC);
        this.wordSize = wordSize;
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        int cellWidth = context.getBank().getCellBitWidth();

        boolean cellSizeIsMultipleOfWordSize = cellWidth % wordSize == 0;
        boolean wordSizeIsMultipleOfCellSize = wordSize % cellWidth == 0;

        int numBits = instruction.getArguments().size() * wordSize;

        if (cellSizeIsMultipleOfWordSize) {
            instruction.setAddress(context.getAddress());
            context.reserveBits(numBits);
        }
        else if (wordSizeIsMultipleOfCellSize) {
            context.alignAddress(wordSize / cellWidth);
            instruction.setAddress(context.getAddress());
            context.reserveBits(numBits);
        }
        else {
            errorReporter.reportError(
                    "Unable to align data: cell width = " + cellWidth
                            + " and word size " + wordSize + " are incompatible.",
                    instruction.getLocation());
        }
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
