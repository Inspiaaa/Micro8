package inspiaaa.assembler.memory;

import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.parser.Location;
import org.w3c.dom.ls.LSOutput;

import java.util.BitSet;
import java.util.HashMap;

public class Memory {
    private static final char[] intToHexCharMap
            = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private final MemoryArchitecture memoryArchitecture;
    private final ErrorReporter errorReporter;
    private final HashMap<String, BitSet> dataById;

    public Memory(MemoryArchitecture memoryArchitecture, ErrorReporter errorReporter) {
        this.memoryArchitecture = memoryArchitecture;
        this.errorReporter = errorReporter;

        this.dataById = new HashMap<>();

        for (MemoryBankInformation bank : memoryArchitecture) {
            dataById.put(bank.getId(), new BitSet(bank.getCellBitWidth() * bank.getSize()));
        }
    }

    public void write(Address address, Location source, boolean[]... bits) {
        var bank = memoryArchitecture.getBank(address.getBankId());
        int bitAddress = address.getAddress() * bank.getCellBitWidth();
        BitSet memory = dataById.get(address.getBankId());

        int totalBitCount = 0;
        for (boolean[] segment : bits) {
            totalBitCount += segment.length;
        }

        ensureHasSpace(bank, address.getAddress(), totalBitCount, source);

        for (boolean[] segment : bits) {
            write(memory, bitAddress, segment);
            bitAddress += segment.length;
        }
    }

    public void ensureHasSpace(MemoryBankInformation bank, int address, int numBits, Location source) {
        if (address < 0) {
            errorReporter.reportError("Invalid storage address (" + address + ").", source);
        }

        int endAddressInBits = address * bank.getCellBitWidth() + numBits;
        int memorySizeInBits = bank.getCellBitWidth() * bank.getSize();

        if (endAddressInBits >= memorySizeInBits) {
            errorReporter.reportError(
                    "Memory overflow. Writing " + numBits + " bit(s) at address " + address
                            + " exceeds available memory space in bank '" + bank.getId() + "'.",
                    source);
        }
    }

    private void write(BitSet memory, int startBit, boolean[] bits) {
        for (int i = 0; i < bits.length; i ++) {
            memory.set(startBit + i, bits[i]);
        }
    }

    public String format(String bankId, boolean asBinary, int charactersPerGroup, int groupsPerLine) {
        var bank = memoryArchitecture.getBank(bankId);
        BitSet memory = dataById.get(bankId);
        int memorySize = bank.getCellBitWidth() * bank.getSize();
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

    public boolean[] integerToBits(Expr expr, int numBits) {
        long value = expr.getNumericValue();

        if (isIntOutOfBounds(value, numBits, false) && isIntOutOfBounds(value, numBits, true)) {
            errorReporter.reportWarning(
                    "Integer " + value + " is out of bounds for " + numBits + "-bit integer.",
                    expr.getLocation());
        }

        return integerToBits(value, numBits);
    }

    public boolean[] integerToBits(Expr expr, int numBits, boolean signed) {
        long value = expr.getNumericValue();

        if (isIntOutOfBounds(value, numBits, signed)) {
            errorReporter.reportWarning(
                "Integer " + value + " is out of bounds for " + numBits + "-bit "
                        + (signed ? "signed" : "unsigned") + " integer.", expr.getLocation());
        }

        return integerToBits(value, numBits);
    }

    private boolean isIntOutOfBounds(long value, int numBits, boolean signed) {
        long minValue = signed
                ? -1L << (numBits - 2)
                : 0;

        long maxValue = signed
                ? (1L << (numBits - 2)) - 1
                : (1L << numBits) - 1;

        return value < minValue || value > maxValue;
    }

    public boolean[] integerToBits(long value, int numBits) {
        // Note: Negative numbers are automatically encoded in 2's complement, as Java encodes
        // negative numbers in 2's complement by default.

        boolean[] bits = new boolean[numBits];

        for (int i = 0; i < numBits; i ++) {
            bits[i] = (value & (1L << i)) != 0;
        }

        return bits;
    }

    public boolean[] toBits(int... bits) {
        boolean[] bitsAsBooleans = new boolean[bits.length];

        for (int i = 0; i < bits.length; i ++) {
            bitsAsBooleans[i] = bits[i] != 0;
        }

        return bitsAsBooleans;
    }
}
