package inspiaaa.assembler.memory;

// Helper class for allocating addresses.
public class AddressContext {
    private final int dataCellBitWidth;
    private final int instructionCellBitWidth;

    private MemorySection section;
    private int address;

    private int lastDataAddress;
    private int lastInstructionAddress;

    public AddressContext(int dataCellBitWidth, int instructionCellBitWidth) {
        this.dataCellBitWidth = dataCellBitWidth;
        this.instructionCellBitWidth = instructionCellBitWidth;
    }

    public void setSection(MemorySection newSection) {
        if (section == newSection)
            return;

        section = newSection;

        if (section == MemorySection.DATA) {
            lastInstructionAddress = address;
            address = lastDataAddress;
        }
        else {
            lastDataAddress = address;
            address = lastInstructionAddress;
        }
    }

    public void reserve(int addresses) {
        address += addresses;
    }

    public void reserveBits(int bits) {
        int width = getCurrentCellWidthInBits();
        address += (bits + width - 1) / width;  // Round up.
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public MemorySection getSection() {
        return section;
    }

    private int getCurrentCellWidthInBits() {
        return switch (section) {
            case DATA -> dataCellBitWidth;
            case INSTRUCTION -> instructionCellBitWidth;
        };
    }
}
