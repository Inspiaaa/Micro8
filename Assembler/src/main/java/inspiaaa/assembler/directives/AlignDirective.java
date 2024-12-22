package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.Instruction;

public class AlignDirective extends Instruction {
    public AlignDirective(String mnemonic) {
        super(mnemonic, ParameterType.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        Expr alignmentExpression = instruction.getArguments().get(0);
        int alignment = (int)alignmentExpression.getNumericValue();

        // Align the next instruction to a multiple of the alignment parameter.
        context.setAddress((context.getAddress().getAddress() + alignment - 1) / alignment * alignment);
    }
}
