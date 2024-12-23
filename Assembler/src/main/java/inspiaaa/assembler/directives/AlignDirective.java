package inspiaaa.assembler.directives;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.Instruction;

public class AlignDirective extends Instruction {
    public AlignDirective(String mnemonic) {
        super(mnemonic, ParameterType.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        // Perform an early type check as this method is called before validate().
        Expr alignmentExpression = instruction.getArguments().get(0);
        TypeChecker.ensureIsNumeric(alignmentExpression, errorReporter);

        int alignment = (int)alignmentExpression.getNumericValue();
        context.alignAddress(alignment);
    }
}
