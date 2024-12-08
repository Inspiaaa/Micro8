package inspiaaa.micro8;

import inspiaaa.assembler.*;

public class Micro8Assembler {
    private Assembler assembler;

    public Micro8Assembler(String code) {
        var arch = new ArchitectureInformation(
                256,
                256,
                8,
                16
        );

        assembler = new Assembler(code, arch);

        assembler.addConstant("x0", SymbolType.REGISTER, 0);
        assembler.addConstant("x1", SymbolType.REGISTER, 1);
        assembler.addConstant("x2", SymbolType.REGISTER, 2);
        assembler.addConstant("x3", SymbolType.REGISTER, 3);
        assembler.addConstant("x4", SymbolType.REGISTER, 4);
        assembler.addConstant("x5", SymbolType.REGISTER, 5);
        assembler.addConstant("x6", SymbolType.REGISTER, 6, "sp");
        assembler.addConstant("x7", SymbolType.REGISTER, 7, "ra");
    }
}
