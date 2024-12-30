package inspiaaa.micro8.instructions;

import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.micro8.StaticAnalysis;

public class JumpInstruction extends ProgramInstruction {
    public JumpInstruction(String mnemonic) {
        super(mnemonic, Parameter.LABEL);
    }

    @Override
    public void validate(InstructionCall instruction) {
        super.validate(instruction);
        Expr absoluteAddress = instruction.getArguments().get(0).unwrap();
        StaticAnalysis.ensureIsInstructionAddress(absoluteAddress, errorReporter);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        Expr absoluteAddress = instruction.getArguments().get(0).unwrap();

        memory.write(instruction.getAddress(), instruction.getLocation(),
                memory.toBits(0, 0, 1, 1),
                memory.toBits(0),
                memory.integerToBits(absoluteAddress, 8, false),
                memory.zeroBits(3));
    }
}
