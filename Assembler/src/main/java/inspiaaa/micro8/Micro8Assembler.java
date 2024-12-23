package inspiaaa.micro8;

import inspiaaa.assembler.*;
import inspiaaa.assembler.directives.*;
import inspiaaa.assembler.memory.MemoryArchitecture;
import inspiaaa.micro8.directives.CheckedAlignedDataDirective;
import inspiaaa.micro8.directives.CheckedUnalignedDataDirective;
import inspiaaa.micro8.instructions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Micro8Assembler {
    public static final String INSTRUCTION_BANK = "instr";
    public static final String DATA_BANK = "data";

    private Assembler assembler;

    public Micro8Assembler(String file, String code) {
        var march = new MemoryArchitecture(INSTRUCTION_BANK);
        march.addBank(INSTRUCTION_BANK, 16, 256);
        march.addBank(DATA_BANK, 8, 256);

        assembler = new Assembler(file, code, march, false);

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

        assembler.defineInstruction(new CheckedUnalignedDataDirective(".byte", 8));
        assembler.defineInstruction(new CheckedUnalignedDataDirective(".2byte", 16));
        assembler.defineInstruction(new CheckedUnalignedDataDirective(".4byte", 32));
        assembler.defineInstruction(new CheckedUnalignedDataDirective(".8byte", 64));

        assembler.defineInstruction(new CheckedAlignedDataDirective(".half", 16));
        assembler.defineInstruction(new CheckedAlignedDataDirective(".word", 32));
        assembler.defineInstruction(new CheckedAlignedDataDirective(".dword", 64));

        assembler.defineInstruction(new ZeroDirective(".zero"));

        assembler.defineInstruction(new VariableDirective(".equ"));

        defineALUInstruction("add", 0);
        defineALUInstruction("sub", 1);
        defineALUInstruction("mul", 2);
        defineALUInstruction("sll", 3);
        defineALUInstruction("srl", 4);
        defineALUInstruction("and", 5);
        defineALUInstruction("or", 6);
        defineALUInstruction("xor", 7);

        assembler.defineInstruction(new LoadByteInstruction("lb"));
        assembler.defineInstruction(new StoreByteInstruction("sb"));

        assembler.defineInstruction(new BranchInstruction("beq", 0));
        assembler.defineInstruction(new BranchInstruction("bne", 1));
        assembler.defineInstruction(new BranchInstruction("bltu", 2));
        assembler.defineInstruction(new BranchInstruction("bgeu", 3));

        assembler.defineInstruction(new JumpInstruction("j"));
        assembler.defineInstruction(new JumpAndLinkInstruction("jal"));
        assembler.defineInstruction(new JumpRegisterInstruction("jr"));
        assembler.defineInstruction(new JumpAndLinkRegisterInstruction("jalr"));

        assembler.defineInstruction(new NoOperationInstruction("nop"));
        assembler.defineInstruction(new LoadImmediateInstruction("li"));
        assembler.defineInstruction(new MoveInstruction("mv"));
    }

    private void defineALUInstruction(String name, int opcode) {
        assembler.defineInstruction(new ALUInstruction(name, opcode));
        assembler.defineInstruction(new ALUImmInstruction(name + "i", opcode));
    }

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/inspiaaa/micro8/example.S";
        String code = Files.readString(Path.of(path));

        var assembler = new Micro8Assembler(path, code);
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
