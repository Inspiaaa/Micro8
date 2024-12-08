package inspiaaa.assembler;

// Helper class for allocating addresses.
public class AddressContext {
    private MemorySection section;
    private int address;

    private int lastDataAddress;
    private int lastInstructionAddress;

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

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public MemorySection getSection() {
        return section;
    }
}
