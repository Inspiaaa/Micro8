package inspiaaa.assembler;

import java.util.HashMap;

public class AssemblerContext {
    private HashMap<String, Instruction> symbolTable;

    private int memoryAddress;
    private int dataAddress;
    private MemorySection section;

    public void switchSection(MemorySection section) {
        this.section = section;
    }
}
