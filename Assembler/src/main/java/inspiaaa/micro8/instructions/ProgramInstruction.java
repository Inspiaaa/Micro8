package inspiaaa.micro8.instructions;

import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class ProgramInstruction extends Instruction {
    public ProgramInstruction(int line) {
        super(line);
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        // TODO: Ensure in INSTRUCTION section
        address = context.getAddress();
        context.reserveBits(16);
    }
}
