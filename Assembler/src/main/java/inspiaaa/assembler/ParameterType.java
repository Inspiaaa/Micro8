package inspiaaa.assembler;

// Instruction parameter types.
public enum ParameterType {
    // +5, -3, 0x160
    IMMEDIATE,
    // 10(sp)
    RELATIVE_ADDRESS,
    // x1
    REGISTER,
    // target
    LABEL,
    // "abc"
    STRING,
    SYMBOL,
    ANY;
}
