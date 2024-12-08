package inspiaaa.assembler;

public enum ParameterType {
    IMMEDIATE,  // +5, -3, 0x160
    RELATIVE_ADDRESS,  // 10(sp)
    REGISTER,  // x1
    LABEL,  // target
    ;
}
