package inspiaaa.assembler;

public class ArchitectureInformation {
    private final int dataMemorySize;
    private final int instructionMemorySize;
    private final int dataCellBitWidth;
    private final int instructionCellBitWidth;

    public ArchitectureInformation(int dataMemorySize, int instructionMemorySize, int dataCellBitWidth, int instructionCellBitWidth) {
        this.dataMemorySize = dataMemorySize;
        this.instructionMemorySize = instructionMemorySize;
        this.dataCellBitWidth = dataCellBitWidth;
        this.instructionCellBitWidth = instructionCellBitWidth;
    }

    public int getInstructionMemorySize() {
        return instructionMemorySize;
    }

    public int getDataMemorySize() {
        return dataMemorySize;
    }

    public int getInstructionCellBitWidth() {
        return instructionCellBitWidth;
    }

    public int getDataCellBitWidth() {
        return dataCellBitWidth;
    }
}
