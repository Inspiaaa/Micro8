package inspiaaa.assembler.directives;

import inspiaaa.assembler.AddressContext;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.MemorySection;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class TextSectionDirective extends Instruction {
    @Override
    public void assignAddress(AddressContext context, SymbolTable symtable, ErrorReporter er) {
        context.setSection(MemorySection.INSTRUCTION);
    }
}
