package inspiaaa.micro8;

import inspiaaa.assembler.Assembler;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryOutputFormat;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "examples/scrolling_image.S";

        Path inputPath = Path.of(path);
        Path instructionOutputPath = Path.of("examples", "output",
                inputPath.getFileName().toString() + ".instr.hex");
        Path dataOutputPath = Path.of("examples", "output",
                inputPath.getFileName().toString() + ".data.hex");
        String code = Files.readString(inputPath);

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

        // Write to file.

        PrintWriter outputWriter = new PrintWriter(instructionOutputPath.toString());
        outputWriter.println("v2.0 raw");
        outputWriter.println(memory.format(
                Micro8Assembler.INSTRUCTION_BANK,
                MemoryOutputFormat.HEX,
                4,
                1,
                true));
        outputWriter.close();

        outputWriter = new PrintWriter(dataOutputPath.toString());
        outputWriter.println("v2.0 raw");
        outputWriter.println(memory.format(
                Micro8Assembler.DATA_BANK,
                MemoryOutputFormat.HEX,
                2,
                1,
                true));
        outputWriter.close();
    }
}
