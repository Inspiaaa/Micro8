package inspiaaa.assembler.memory;

public class MemoryBankInformation {
    private final String id;
    private final int cellBitWidth;
    private final int size;

    public MemoryBankInformation(String id, int cellBitWidth, int size) {
        this.id = id;
        this.cellBitWidth = cellBitWidth;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public int getCellBitWidth() {
        return cellBitWidth;
    }

    public int getSize() {
        return size;
    }
}
