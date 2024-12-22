package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.ErrorReporter;

public class OrgDirective extends Instruction {
    public OrgDirective(String mnemonic) {
        super(mnemonic, ParameterType.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        int address = (int)instruction.getArguments().get(0).getNumericValue();
        context.setAddress(address);
    }
}
