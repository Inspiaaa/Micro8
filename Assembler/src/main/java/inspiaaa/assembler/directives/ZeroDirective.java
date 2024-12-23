package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.TypeChecker;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryBankInformation;

public class ZeroDirective extends Instruction {
    public ZeroDirective(String mnemonic) {
        super(mnemonic, ParameterType.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        // Perform an early type check as this method is called before validate().
        Expr addressCountExpr = instruction.getArguments().get(0);
        TypeChecker.ensureIsNumeric(addressCountExpr, errorReporter);

        instruction.setAddress(context.getAddress());
        context.reserve((int)addressCountExpr.getNumericValue());
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        MemoryBankInformation bank = memory.getMemoryArchitecture().getBank(instruction.getAddress().getBankId());
        int cells = (int)instruction.getArguments().get(0).getNumericValue();
        int bits = bank.getCellBitWidth() * cells;
        memory.write(instruction.getAddress(), instruction.getLocation(), new boolean[bits]);
    }
}
