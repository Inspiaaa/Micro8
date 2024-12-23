package inspiaaa.assembler.memory;

import java.util.HashMap;

// Helper class for allocating addresses.
public class AddressContext {
    private final MemoryArchitecture memoryArchitecture;
    private final HashMap<String, Integer> lastAddressByBank;

    private MemoryBankInformation currentBank;
    private int currentAddress;

    public AddressContext(MemoryArchitecture memoryArchitecture) {
        this.memoryArchitecture = memoryArchitecture;
        this.lastAddressByBank = new HashMap<>();
        this.currentBank = memoryArchitecture.getDefaultBank();
    }

    public void setBank(String id) {
        if (currentBank.getId().equals(id))
            return;

        lastAddressByBank.put(currentBank.getId(), currentAddress);
        currentBank = memoryArchitecture.getBank(id);
        currentAddress = lastAddressByBank.getOrDefault(id, 0);
    }

    public void reserve(int addresses) {
        currentAddress += addresses;
    }

    public void reserveBits(int bits) {
        int width = currentBank.getCellBitWidth();
        currentAddress += (bits + width - 1) / width;  // Round up.
    }

    public void setAddress(int address) {
        this.currentAddress = address;
    }

    public void alignAddress(int alignment) {
        currentAddress = (currentAddress + alignment - 1) / alignment * alignment;
    }

    public Address getAddress() {
        return new Address(currentAddress, currentBank.getId());
    }

    public String getBankId() {
        return currentBank.getId();
    }

    public MemoryBankInformation getBank() {
        return currentBank;
    }
}
