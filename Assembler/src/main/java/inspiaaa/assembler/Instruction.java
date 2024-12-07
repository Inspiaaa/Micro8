package inspiaaa.assembler;

public class Instruction {
    // Everything is an instruction:
    // - Labels
    // - Assembler directives
    // - Actual instructions

    private int address;

    public void preprocess() {
        // Labels register themselves into the symbol table here.
    }

    public void allocateAddress() {

    }

    public void lower() {

    }

    public void compile() {

    }
}
