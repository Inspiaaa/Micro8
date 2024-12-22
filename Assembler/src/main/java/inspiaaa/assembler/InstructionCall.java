package inspiaaa.assembler;

import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.memory.Address;
import inspiaaa.assembler.parser.Location;

import java.util.List;

public class InstructionCall {
    // Raw information obtained from parsing.
    private final String mnemonic;
    private final List<Expr> arguments;
    private final Location location;

    // Additional information produced by the assembler.
    private Instruction instructionDefinition;
    private Address address;

    public InstructionCall(String mnemonic, List<Expr> arguments, Location location) {
        this.mnemonic = mnemonic;
        this.arguments = arguments;
        this.location = location;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public List<Expr> getArguments() {
        return arguments;
    }

    public Location getLocation() {
        return location;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Instruction getInstructionDefinition() {
        return instructionDefinition;
    }

    public void setInstructionDefinition(Instruction instructionDefinition) {
        this.instructionDefinition = instructionDefinition;
    }

    @Override
    public String toString() {
        if (arguments.isEmpty()) {
            return mnemonic;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(mnemonic);

        sb.append(" ");
        sb.append(arguments.get(0));

        for (int i = 1; i < arguments.size(); i++) {
            sb.append(", ");
            sb.append(arguments.get(i));
        }

        return sb.toString();
    }
}
