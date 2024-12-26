package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public class InstructionReferenceExpr extends Expr {
    private final String mnemonic;

    public InstructionReferenceExpr(String mnemonic) {
        this(mnemonic, null);
    }

    public InstructionReferenceExpr(String mnemonic, Location location) {
        super(location);
        this.mnemonic = mnemonic;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public Expr withLocation(Location location) {
        return new InstructionReferenceExpr(mnemonic, location);
    }

    @Override
    public String toString() {
        return "INSTRUCTION(" + mnemonic + ")";
    }
}
