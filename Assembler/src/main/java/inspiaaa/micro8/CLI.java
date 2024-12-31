package inspiaaa.micro8;

import inspiaaa.assembler.Assembler;
import inspiaaa.assembler.memory.Memory;
import inspiaaa.assembler.memory.MemoryOutputFormat;
import inspiaaa.cli.CliOptions;
import inspiaaa.cli.CliParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;


public class CLI {
    private static final String helpMessage = """
usage: assembler input_file [-i FILE] [-d FILE] [-v]

positional arguments:
  input_file              assembly file to process

options:
  -h, --help              show this help message an exit
  -i, --instruction FILE  output path for the instruction memory
  -d, --data FILE         output path for the data memory
  -v, --verbose           visualize the instruction and data memories
""";

    public static void main(String[] args) throws IOException {
        CliParser parser = new CliParser(helpMessage);

        parser.addArgument("file");
        parser.addOption("-d", "--data");
        parser.addOption("-i", "--instruction");
        parser.addFlag("-v", "--verbose");

        CliOptions options = parser.parse(args);

        String file = options.get("file");
        Path inputPath = Path.of(file);
        String code = Files.readString(inputPath);

        Assembler assembler = Micro8Assembler.createAssembler(file, code);
        assembler.assemble();
        Memory memory = assembler.getMemory();

        if (options.isFlagSet("verbose")) {
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

        if (options.hasOption("data")) {
            PrintWriter outputWriter = new PrintWriter(options.get("data"));
            outputWriter.println("v2.0 raw");
            outputWriter.println(memory.format(
                    Micro8Assembler.INSTRUCTION_BANK,
                    MemoryOutputFormat.HEX,
                    4,
                    1,
                    true));
            outputWriter.close();
        }

        if (options.hasOption("instruction")) {
            PrintWriter outputWriter = new PrintWriter(options.get("instruction"));
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
}
