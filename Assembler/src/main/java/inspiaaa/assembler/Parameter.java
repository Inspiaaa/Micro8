package inspiaaa.assembler;

import inspiaaa.assembler.expressions.*;
import inspiaaa.assembler.typing.*;

// Provides quick access to common ParameterType checkers.
public class Parameter {
    public static final ParameterType
            IMMEDIATE = new ImmediateParameterType(),
            SYMBOL = new SymbolTypeParameter(),
            ANY = new AnyParameterType(),
            LABEL = new LabelParameterType(),
            NUMERIC = new NumericParameterType(),
            REGULAR_NUMBER = new RegularNumberParameterType(),
            STRING = new ExactClassParameterType(StringExpr.class, "string");

    public static ParameterType distinctInteger(String type) {
        return new DistinctIntegerParameterType(type);
    }

    public static ParameterType relativeAddress(String registerType) {
        return new RelativeAddressParameterType(registerType);
    }
}
