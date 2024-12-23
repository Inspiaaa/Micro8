package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.ParameterType;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryBankInformation;

public class ZeroDirective extends Instruction {
    public ZeroDirective(String mnemonic) {
        super(mnemonic, ParameterType.IMMEDIATE);
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        // TODO: Perform argument check here (and in similar instructions that use numbers in assignAddress)
        // or maybe introduce mechanism for early validation

        int addresses = (int)instruction.getArguments().get(0).getNumericValue();
        instruction.setAddress(context.getAddress());
        context.reserve(addresses);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        MemoryBankInformation bank = memory.getMemoryArchitecture().getBank(instruction.getAddress().getBankId());
        int cells = (int)instruction.getArguments().get(0).getNumericValue();
        int bits = bank.getCellBitWidth() * cells;
        memory.write(instruction.getAddress(), instruction.getLocation(), new boolean[bits]);
    }
}
