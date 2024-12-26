package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public class InstructionReferenceExpr extends Expr {
    private final String name;

    public InstructionReferenceExpr(String name) {
        this(name, null);
    }

    public InstructionReferenceExpr(String name, Location location) {
        super(location);
        this.name = name;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public Expr withLocation(Location location) {
        return new InstructionReferenceExpr(name, location);
    }

    @Override
    public String toString() {
        return "INSTRUCTION(" + name + ")";
    }
}
