package inspiaaa.assembler;

import inspiaaa.assembler.parser.ErrorReporter;

import java.util.List;

public class Instruction {
    // Everything is an instruction:
    // - Labels
    // - Assembler directives
    // - Actual instructions

    protected int address = -1;
    protected int line;

    public Instruction(int line) {
        this.line = line;
    }

    // Execution order:
    // 1. Lower.
    // 2. Assign addresses.
    // 3. Preprocess (in parallel to above, but after each assignAddress call).
    // 4. Compile.

    public void preprocess(SymbolTable symtable) {

    }

    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        this.address = context.getAddress();
    }

    public List<Instruction> lower() {
        return null;
    }

    public void compile() {

    }
}
