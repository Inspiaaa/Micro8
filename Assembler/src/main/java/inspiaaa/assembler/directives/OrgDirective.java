package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.Instruction;

public class OrgDirective extends Instruction {
    public OrgDirective(String mnemonic) {
        super(mnemonic, Parameter.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        int address = (int)instruction.getArguments().get(0).getNumericValue();
        context.setAddress(address);
    }
}
