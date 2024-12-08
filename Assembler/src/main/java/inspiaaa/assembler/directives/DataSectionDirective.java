package inspiaaa.assembler.directives;

import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.memory.MemorySection;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class DataSectionDirective extends Instruction {
    public DataSectionDirective(int line) {
        super(line);
    }

    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        context.setSection(MemorySection.DATA);
    }
}
