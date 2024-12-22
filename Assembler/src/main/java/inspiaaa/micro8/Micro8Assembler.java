package inspiaaa.micro8;

import inspiaaa.assembler.*;
import inspiaaa.assembler.directives.AlignDirective;
import inspiaaa.assembler.directives.OrgDirective;
import inspiaaa.assembler.directives.MemoryBankDirective;
import inspiaaa.assembler.memory.MemoryArchitecture;
import inspiaaa.micro8.instructions.ALUImmInstruction;
import inspiaaa.micro8.instructions.ALUInstruction;
import inspiaaa.micro8.instructions.LoadByteInstruction;

public class Micro8Assembler {
    public static final String INSTRUCTION_BANK = "instr";
    public static final String DATA_BANK = "data";

    private Assembler assembler;

    public Micro8Assembler(String file, String code) {
        var march = new MemoryArchitecture(INSTRUCTION_BANK);
        march.addBank(INSTRUCTION_BANK, 16, 256);
        march.addBank(DATA_BANK, 8, 256);

        assembler = new Assembler(file, code, march);

        assembler.defineConstant("x0", SymbolType.REGISTER, 0);
        assembler.defineConstant("x1", SymbolType.REGISTER, 1);
        assembler.defineConstant("x2", SymbolType.REGISTER, 2);
        assembler.defineConstant("x3", SymbolType.REGISTER, 3);
        assembler.defineConstant("x4", SymbolType.REGISTER, 4);
        assembler.defineConstant("x5", SymbolType.REGISTER, 5);
        assembler.defineConstant("x6", SymbolType.REGISTER, 6, "sp");
        assembler.defineConstant("x7", SymbolType.REGISTER, 7, "ra");

        assembler.defineInstruction(new OrgDirective(".org"));
        assembler.defineInstruction(new MemoryBankDirective(".data", DATA_BANK));
        assembler.defineInstruction(new MemoryBankDirective(".text", INSTRUCTION_BANK));
        assembler.defineInstruction(new AlignDirective(".align"));

        defineALUInstruction("add", 0);
        defineALUInstruction("sub", 1);
        defineALUInstruction("mul", 2);
        defineALUInstruction("sll", 3);
        defineALUInstruction("srl", 4);
        defineALUInstruction("and", 5);
        defineALUInstruction("or", 6);
        defineALUInstruction("xor", 7);

        assembler.defineInstruction(new LoadByteInstruction("lb"));
    }

    private void defineALUInstruction(String name, int opcode) {
        assembler.defineInstruction(new ALUInstruction(name, opcode));
        assembler.defineInstruction(new ALUImmInstruction(name, opcode));
    }

    public static void main(String[] args) {
        // TODO: Instructions with variadic argument count (esp. for .byte directive)
        // TODO: String literals
        // TODO: Arch info for start address for data and instruction sections, start memory section.

        String code = """
start:
addi x0, -15
add sp, x0, x1
addi ra, 0b101

# Comment

lb x0, 10(x1) # Comment
.org 0x50
""";

        var assembler = new Micro8Assembler("main.S", code);
        assembler.assembler.assemble();

        System.out.println();

        System.out.println("INSTRUCTION");
        System.out.println(assembler.assembler.getMemory().format(INSTRUCTION_BANK, true, 16, 4));
        // System.out.println(assembler.assembler.getMemory().format(MemorySection.INSTRUCTION, false, 4, 16));

        System.out.println();

        System.out.println("DATA");
        System.out.println(assembler.assembler.getMemory().format(DATA_BANK, false, 2, 16));
    }
}
