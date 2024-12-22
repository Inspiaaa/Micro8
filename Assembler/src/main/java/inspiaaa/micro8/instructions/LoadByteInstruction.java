package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.expressions.RelativeAddressExpr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.parser.ErrorReporter;

public class LoadByteInstruction extends ProgramInstruction {
    public LoadByteInstruction(String mnemonic) {
        super(mnemonic, ParameterType.REGISTER, ParameterType.RELATIVE_ADDRESS);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr register = instruction.getArguments().get(0);
        RelativeAddressExpr relativeAddress = (RelativeAddressExpr) instruction.getArguments().get(1);

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 0, 0),
                memory.integerToBits(relativeAddress.getOffset(), 5, false),
                memory.integerToBits(relativeAddress.getBase(), 3, false),
                memory.integerToBits(register, 3, false));
    }
}
