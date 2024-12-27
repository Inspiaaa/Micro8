package inspiaaa.micro8;

import inspiaaa.assembler.*;
import inspiaaa.assembler.directives.*;
import inspiaaa.assembler.memory.MemoryArchitecture;
import inspiaaa.assembler.typing.ParameterType;
import inspiaaa.micro8.directives.CheckedAlignedDataDirective;
import inspiaaa.micro8.directives.CheckedAsciiDirective;
import inspiaaa.micro8.directives.CheckedUnalignedDataDirective;
import inspiaaa.micro8.instructions.*;
import inspiaaa.micro8.pseudo.*;

public class Micro8Assembler {
    public static final String INSTRUCTION_BANK = "instr";
    public static final String DATA_BANK = "data";

    public static final String REGISTER = "register";
    public static final ParameterType REGISTER_TYPE = Parameter.distinctNumber(REGISTER);

    public static Assembler createAssembler(String file, String code) {
        var march = new MemoryArchitecture(INSTRUCTION_BANK);
        march.addBank(INSTRUCTION_BANK, 16, 256);
        march.addBank(DATA_BANK, 8, 256);

        Assembler assembler = new Assembler(file, code, march, false);

        assembler.defineConstant("x1", REGISTER, 0);
        assembler.defineConstant("x2", REGISTER, 1);
        assembler.defineConstant("x3", REGISTER, 2);
        assembler.defineConstant("x4", REGISTER, 3);
        assembler.defineConstant("x5", REGISTER, 4);
        assembler.defineConstant("x6", REGISTER, 5);
        assembler.defineConstant("x7", REGISTER, 6);
        assembler.defineConstant("x8", REGISTER, 7);

        assembler.defineConstantSynonym("x7", "sp");
        assembler.defineConstantSynonym("x8", "ra");

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

        assembler.defineInstruction(new CheckedAsciiDirective(".ascii", false));
        assembler.defineInstruction(new CheckedAsciiDirective(".asciz", true));

        assembler.defineInstruction(new VariableDirective(".equ"));

        defineALUInstruction(assembler, "add", 0);
        defineALUInstruction(assembler, "sub", 1);
        defineALUInstruction(assembler, "mul", 2);
        defineALUInstruction(assembler, "sll", 3);
        defineALUInstruction(assembler, "srl", 4);
        defineALUInstruction(assembler, "and", 5);
        defineALUInstruction(assembler, "or", 6);
        defineALUInstruction(assembler, "xor", 7);

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

        assembler.defineInstruction(new CallPseudoInstruction("call"));
        assembler.defineInstruction(new ReturnPseudoInstruction("ret"));
        assembler.defineInstruction(new NotPseudoInstruction("not"));
        assembler.defineInstruction(new LoadAddressPseudoInstruction("la"));
        assembler.defineInstruction(new BranchPseudoInstruction("bleu", "bgeu"));
        assembler.defineInstruction(new BranchPseudoInstruction("bgtu", "bltu"));

        assembler.defineConstant("IO", 247);
        assembler.defineConstant("IN_A", 7);
        assembler.defineConstant("IN_B", 8);
        assembler.defineConstant("OUT_0", 0);
        assembler.defineConstant("OUT_1", 1);
        assembler.defineConstant("OUT_2", 2);
        assembler.defineConstant("OUT_3", 3);
        assembler.defineConstant("OUT_4", 4);
        assembler.defineConstant("OUT_5", 5);
        assembler.defineConstant("STACK_END", 246);

        return assembler;
    }

    private static void defineALUInstruction(Assembler assembler, String name, int opcode) {
        assembler.defineInstruction(new ALUInstruction(name, opcode));
        assembler.defineInstruction(new ALUImmInstruction(name + "i", opcode));
    }
}
