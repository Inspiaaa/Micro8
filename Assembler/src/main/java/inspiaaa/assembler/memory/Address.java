package inspiaaa.assembler.memory;

public class Address {
    private int value;
    private MemorySection section;

    public Address(int value, MemorySection section) {
        this.value = value;
        this.section = section;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public MemorySection getSection() {
        return section;
    }

    public void setSection(MemorySection section) {
        this.section = section;
    }
}
