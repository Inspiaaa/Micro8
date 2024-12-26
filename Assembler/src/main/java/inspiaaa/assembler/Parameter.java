package inspiaaa.assembler;

import inspiaaa.assembler.expressions.*;
import inspiaaa.assembler.typing.*;

public class Parameter {
    public static final ParameterTypeChecker
            IMMEDIATE = new ImmediateParameterType(),
            SYMBOL = new SymbolTypeParameter(),
            ANY = new AnyParameterType(),
            LABEL = new LabelParameterType(),
            NUMERIC = new NumericParameterType(),
            STRING = new ExactClassParameterType(StringExpr.class, "string");

    public static ParameterTypeChecker distinctNumber(String type) {
        return new DistinctNumberParameterType(type);
    }

    public static ParameterTypeChecker relativeAddress(String registerType) {
        return new RelativeAddressParameterType(registerType);
    }
}
