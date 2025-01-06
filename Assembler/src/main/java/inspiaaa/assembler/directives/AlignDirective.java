package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.Instruction;

public class AlignDirective extends Instruction {
    public AlignDirective(String mnemonic) {
        super(mnemonic, Parameter.REGULAR_NUMBER);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        // Perform an early type check as this method is called before the regular validate().
        super.validate(instruction);
        Expr alignmentExpression = instruction.getArguments().get(0);

        int alignment = (int)alignmentExpression.getNumericValue();
        context.alignAddress(alignment);
    }

    @Override
    public void validate(InstructionCall instruction) {
        // Nothing to do, as the regular validation already took place during the assignAddress call.
        // Checking again may produce the same warning again.
    }
}
