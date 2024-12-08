package inspiaaa.assembler.memory;

import inspiaaa.assembler.parser.ErrorReporter;

import java.util.BitSet;

public class Memory {
    private static final char[] intToHexCharMap
            = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

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

    public String format(MemorySection section, boolean asBinary, int charactersPerGroup, int groupsPerLine) {
        BitSet memory = null;
        int memorySize = -1;

        switch (section) {
            case DATA:
                memory = dataMemory;
                memorySize = dataMemorySize * dataCellBitWidth;
                break;
            case INSTRUCTION:
                memory = instructionMemory;
                memorySize = instructionMemorySize * instructionCellBitWidth;
        }

        String text = asBinary ? formatAsBinary(memory, memorySize) : formatAsHex(memory, memorySize);
        return formatPretty(text, charactersPerGroup, groupsPerLine);
    }

    private String formatAsBinary(BitSet bits, int size) {
        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i ++) {
            result.append(bits.get(i) ? '1' : '0');
        }
        return result.toString();
    }

    private String formatAsHex(BitSet bits, int size) {
        StringBuilder result = new StringBuilder(size);

        for (int i = 0; i < size; i += 4) {
            result.append(bitsToHexChar(bits, size, i));
        }

        return result.toString();
    }

    private char bitsToHexChar(BitSet bits, int size, int bitAddress) {
        byte value = 0;

        for (int i = 0; i < 4; i ++) {
            int address = bitAddress + i;
            if (address >= size) break;

            value += (bits.get(address) ? 1 : 0) << i;
        }

        return intToHexCharMap[value];
    }

    private String formatPretty(String rawData, int charactersPerGroup, int groupsPerLine) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rawData.length(); i ++) {
            if (i > 0) {
                if (i % (charactersPerGroup * groupsPerLine) == 0) {
                    result.append('\n');
                }
                else if (i % charactersPerGroup == 0) {
                    result.append(' ');
                }
            }

            result.append(rawData.charAt(i));
        }

        return result.toString();
    }

    public static boolean[] integerToBits(long value, int numBits) {
        // Note: Negative numbers are automatically encoded in 2's complement, as Java encodes
        // negative numbers in 2's complement by default.

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
