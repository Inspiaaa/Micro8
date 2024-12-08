package inspiaaa.assembler.memory;

import java.util.BitSet;

public class Memory {
    private final int dataMemorySize;
    private final int instructionMemorySize;
    private final int dataCellBitWidth;
    private final int instructionCellBitWidth;

    private final BitSet dataMemory;
    private final BitSet programMemory;

    public Memory(int dataMemorySize, int instructionMemorySize, int dataCellBitWidth, int instructionCellBitWidth) {
        this.dataMemorySize = dataMemorySize;
        this.instructionMemorySize = instructionMemorySize;
        this.dataCellBitWidth = dataCellBitWidth;
        this.instructionCellBitWidth = instructionCellBitWidth;

        this.dataMemory = new BitSet(dataMemorySize * dataCellBitWidth);
        this.programMemory = new BitSet(instructionMemorySize * instructionCellBitWidth);
    }

    public void write(Address address, byte data) {

    }
}
