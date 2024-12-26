package inspiaaa.assembler;

import inspiaaa.assembler.expressions.*;
import inspiaaa.assembler.typing.*;

public class Parameter {
    public static final ArgumentTypeChecker
            IMMEDIATE = new ImmediateParameterType(),
            SYMBOL = new SymbolTypeParameter(),
            ANY = new AnyParameterType(),
            LABEL = new LabelParameterType(),
            NUMERIC = new NumericParameterType(),
            STRING = new ExactClassParameterType(StringExpr.class, "string");

    public static ArgumentTypeChecker distinctNumber(String type) {
        return new DistinctNumberParameterType(type);
    }

    public static ArgumentTypeChecker relativeAddress(String registerType) {
        return new RelativeAddressParameterType(registerType);
    }
}
