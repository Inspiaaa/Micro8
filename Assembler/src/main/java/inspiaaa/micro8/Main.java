package inspiaaa.micro8;

import inspiaaa.assembler.Assembler;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryOutputFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "examples/io_example.S";
        String code = Files.readString(Path.of(path));

        Assembler assembler = Micro8Assembler.createAssembler(path, code);
        assembler.assemble();
        Memory memory = assembler.getMemory();

        System.out.println();

        System.out.println("INSTRUCTION");
        System.out.println(memory.format(
                Micro8Assembler.INSTRUCTION_BANK,
                MemoryOutputFormat.BINARY,
                16,
                4,
                false));

        System.out.println();

        System.out.println("DATA");
        System.out.println(memory.format(
                Micro8Assembler.DATA_BANK,
                MemoryOutputFormat.HEX,
                2,
                16,
                true));
    }
}
