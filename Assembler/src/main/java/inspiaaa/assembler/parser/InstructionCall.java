package inspiaaa.assembler.parser;

import inspiaaa.assembler.expressions.Expr;

import java.util.List;

public class InstructionCall {
    private final String mnemonic;
    private final List<Expr> arguments;
    private final Location location;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(mnemonic);

        sb.append(" ");

        for (int i = 0; i < arguments.size() - 1; i++) {
            sb.append(arguments.get(i));
            sb.append(", ");
        }

        if (!arguments.isEmpty()) {
            sb.append(arguments.get(arguments.size() - 1));
        }

        return sb.toString();
    }
}
