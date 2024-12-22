package inspiaaa.assembler.memory;

public class Address {
    private final int address;
    private final String bankId;

    public Address(int address, String bankId) {
        this.address = address;
        this.bankId = bankId;
    }

    public int getAddress() {
        return address;
    }

    public String getBankId() {
        return bankId;
    }

    @Override
    public String toString() {
        return bankId + "[" + address + "]";
    }
}
