package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Lowerable;

import java.util.List;

public class SynonymPseudoInstruction extends Instruction implements Lowerable {
    private final String primaryMnemonic;

    public SynonymPseudoInstruction(String mnemonic, Instruction instruction) {
        super(mnemonic, instruction.isVariadic(), instruction.getParameters());
        this.primaryMnemonic = instruction.getMnemonic();
    }

    @Override
    public List<InstructionCall> lower(InstructionCall instruction) {
        return List.of(new InstructionCall(
                primaryMnemonic,
                instruction.getArguments(),
                instruction.getLocation()));
    }
}
