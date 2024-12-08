package inspiaaa.assembler.memory;

import inspiaaa.assembler.parser.ErrorReporter;

import java.util.BitSet;

public class Memory {
    private final int dataMemorySize;
    private final int instructionMemorySize;
    private final int dataCellBitWidth;
    private final int instructionCellBitWidth;

    private final BitSet dataMemory;
    private final BitSet instructionMemory;

    private final ErrorReporter errorReporter;

    public Memory(
            int dataMemorySize,
            int instructionMemorySize,
            int dataCellBitWidth,
            int instructionCellBitWidth,
            ErrorReporter errorReporter) {

        this.dataMemorySize = dataMemorySize;
        this.instructionMemorySize = instructionMemorySize;
        this.dataCellBitWidth = dataCellBitWidth;
        this.instructionCellBitWidth = instructionCellBitWidth;
        this.errorReporter = errorReporter;

        this.dataMemory = new BitSet(dataMemorySize * dataCellBitWidth);
        this.instructionMemory = new BitSet(instructionMemorySize * instructionCellBitWidth);
    }

    public void write(Address address, int line, boolean[]... bits) {
        BitSet memory = null;
        int bitAddress = -1;

        switch (address.getSection()) {
            case DATA: {
                bitAddress = address.getValue() * dataCellBitWidth;
                memory = dataMemory;
                break;
            }
            case INSTRUCTION: {
                bitAddress = address.getValue() * instructionCellBitWidth;
                memory = instructionMemory;
                break;
            }
        }

        int totalBitCount = 0;
        for (boolean[] segment : bits) {
            totalBitCount += segment.length;
        }

        ensureHasSpace(address, totalBitCount, line);

        for (boolean[] segment : bits) {
            write(memory, bitAddress, segment);
            bitAddress += segment.length;
        }
    }

    public void ensureHasSpace(Address address, int numBits, int line) {
        int addressValue = address.getValue();

        if (addressValue < 0) {
            errorReporter.reportError("Invalid storage address (" + addressValue + ").", line);
        }

        int cellBitWidth = 0;
        int memorySizeInBits = 0;

        switch (address.getSection()) {
            case DATA:
                cellBitWidth = dataCellBitWidth;
                memorySizeInBits = dataMemorySize * cellBitWidth;
                break;
            case INSTRUCTION:
                cellBitWidth = instructionCellBitWidth;
                memorySizeInBits = instructionMemorySize * cellBitWidth;
        }

        int endAddressInBits = addressValue * cellBitWidth + numBits;

        if (endAddressInBits >= memorySizeInBits) {
            errorReporter.reportError(
                    "Memory overflow. Address (" + addressValue + ") exceeds available memory space.", line);
        }
    }

    private void write(BitSet memory, int startBit, boolean[] bits) {
        for (int i = 0; i < bits.length; i ++) {
            memory.set(startBit + i, bits[i]);
        }
    }

    public static boolean[] integerToBits(long value, int numBits) {
        boolean[] bits = new boolean[numBits];

        for (int i = 0; i < numBits; i ++) {
            bits[i] = (value & (1L << i)) != 0;
        }

        return bits;
    }

    public static boolean[] toBits(int... bits) {
        boolean[] bitsAsBooleans = new boolean[bits.length];

        for (int i = 0; i < bits.length; i ++) {
            bitsAsBooleans[i] = bits[i] != 0;
        }

        return bitsAsBooleans;
    }
}
