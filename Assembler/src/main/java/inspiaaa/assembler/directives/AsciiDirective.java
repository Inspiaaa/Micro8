package inspiaaa.assembler.directives;

import inspiaaa.assembler.Instruction;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.Parameter;
import inspiaaa.assembler.expressions.StringExpr;
import inspiaaa.assembler.memory.AddressContext;
import inspiaaa.assembler.memory.Memory;

public class AsciiDirective extends Instruction {
    protected final boolean zeroDelimiter;

    public AsciiDirective(String mnemonic, boolean zeroDelimiter) {
        super(mnemonic, Parameter.STRING);
        this.zeroDelimiter = zeroDelimiter;
    }

    private String getStringArgument(InstructionCall instruction) {
        return ((StringExpr) instruction.getArguments().get(0).unwrap()).getValue();
    }

    @Override
    public void assignAddress(InstructionCall instruction, AddressContext context) {
        String text = getStringArgument(instruction);
        int numBytes = text.length() + (zeroDelimiter ? 1 : 0);

        instruction.setAddress(context.getAddress());
        context.reserveBits(numBytes * 8);
    }

    @Override
    public void compile(InstructionCall instruction, Memory memory) {
        String text = getStringArgument(instruction);
        int numBytes = text.length() + (zeroDelimiter ? 1 : 0);

        boolean[][] binaryData = new boolean[numBytes][];

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            binaryData[i] = memory.integerToBits((byte)c, 8);
        }

        if (zeroDelimiter) {
            binaryData[binaryData.length - 1] = memory.zeroBits(8);
        }

        memory.write(instruction.getAddress(), instruction.getLocation(), binaryData);
    }
}
