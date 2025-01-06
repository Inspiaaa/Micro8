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
        // Note that although this method is called before validate(), no early validation is required:
        // If a symbol that is not defined yet is passed as a parameter, then it will pass the "potentiallyMatches"
        // logic of the parameter checker.
        // - However, it does not matter if the symbol receives an incompatible type later, as it does not wait
        //   for the symbol to be resolved, since it performs its "work" during assignAddress(). Thus, instead
        //   of throwing an internal error due to an incompatible type, it will report a "symbol not found" error.
        // - If the symbol is given an incompatible type before this directive, then it will not pass the parameter
        //   type checker.

        Expr alignmentExpression = instruction.getArguments().get(0);

        int alignment = (int)alignmentExpression.getNumericValue();
        context.alignAddress(alignment);
    }
}
