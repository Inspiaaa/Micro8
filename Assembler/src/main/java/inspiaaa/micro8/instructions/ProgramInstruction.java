package inspiaaa.micro8.instructions;

import inspiaaa.assembler.SymbolType;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.memory.MemorySection;
import inspiaaa.assembler.parser.ErrorReporter;

public class ProgramInstruction extends Instruction {
    public ProgramInstruction(int line) {
        super(line);
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        address = context.getAddress();
        context.reserveBits(16);
    }

    @Override
    public void validate(SymbolTable symtable, ErrorReporter er) {
        if (address.getSection() != MemorySection.INSTRUCTION) {
            er.reportWarning("Writing instruction to data section. Use '.text' to switch to instruction section.", line);
        }
    }
}
