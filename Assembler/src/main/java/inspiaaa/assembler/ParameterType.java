package inspiaaa.assembler;

// Instruction parameter types.
public enum ParameterType {
    // Numeric types:
    IMMEDIATE,  // 234, 0b101, 'a', ...
    REGISTER,  // x1, ra
    LABEL,  // target:

    // True types (argument must match the type exactly):
    RELATIVE_ADDRESS, // 10(sp)
    STRING,  // "abc"
    SYMBOL,

    ANY;
}
