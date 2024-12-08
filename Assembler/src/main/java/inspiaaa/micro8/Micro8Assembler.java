package inspiaaa.micro8;

import inspiaaa.assembler.*;
import inspiaaa.assembler.directives.AlignDirective;
import inspiaaa.assembler.directives.DataSectionDirective;
import inspiaaa.assembler.directives.OrgDirective;
import inspiaaa.assembler.directives.TextSectionDirective;

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

        assembler.defineConstant("x0", SymbolType.REGISTER, 0);
        assembler.defineConstant("x1", SymbolType.REGISTER, 1);
        assembler.defineConstant("x2", SymbolType.REGISTER, 2);
        assembler.defineConstant("x3", SymbolType.REGISTER, 3);
        assembler.defineConstant("x4", SymbolType.REGISTER, 4);
        assembler.defineConstant("x5", SymbolType.REGISTER, 5);
        assembler.defineConstant("x6", SymbolType.REGISTER, 6, "sp");
        assembler.defineConstant("x7", SymbolType.REGISTER, 7, "ra");

        assembler.defineInstruction(
                ".org",
                (args, line) -> new OrgDirective(args.get(0), line),
                ParameterType.IMMEDIATE);

        assembler.defineInstruction(
                ".data",
                (args, line) -> new DataSectionDirective(line));

        assembler.defineInstruction(
                ".text",
                (args, line) -> new TextSectionDirective(line));

        assembler.defineInstruction(
                ".align",
                (args, line) -> new AlignDirective(args.get(0), line),
                ParameterType.IMMEDIATE);
    }

    public static void main(String[] args) {
        String code = """
start:
addi r0, 15
addi sp, 0b101

# Comment

lw x0, 10(x1) # Comment
.org 0x50
""";

        var assembler = new Micro8Assembler(code);
    }
}
