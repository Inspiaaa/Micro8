# Usage Tutorial

If you simply want to test the default example program ([`io_maths.S`](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/examples/io_maths.S)), then skip to step 4.

## 1. Writing your own assembly program

Before you start:
- See the *[ISA documentation](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/ISA.md)* for an overview over the available features and instructions of the computer.
- Learn more about the features of the assembly language and the built-in directives in the *[assembler reference](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Assembly%20Reference.md)*.
- Have a look at some of the [example programs](https://github.com/Inspiaaa/Micro8/tree/master/Assembler/examples) to see how to implement common functionalities. Notable files:
	- [Reading inputs and writing to screen](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/examples/io_example.S)
	- [Recursive function using the stack](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/examples/recursive_fib.S)
	- [Advanced example](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/examples/io_maths.S)

How to write your own program:

1. Create a new text file (any extension is fine).
2. Write your assembly program.
3. Save the file to disk.

## 2. Translating to machine code

As the computer has two memory units (instruction and data memory), two data files have to be generated that can be later loaded into the computer separately.

To assemble your program and generate these files you have two options:

### Use the CLI

1. Download the `.jar` assembler file from the [releases](https://github.com/Inspiaaa/Micro8/releases).
2. Run the assembler on your file, specifying the output path of the instruction (`-i path`) and of the data memory (`-d path`) files. These files should have a `.hex` file extension so that they can be loaded into Digital.
	```
	java -jar assembler.jar file.S -i file.instr.hex -d file.data.hex
	```

### Update the `Main.java` program.

1. Download the java project to your computer.
2. Edit the [`Main.java`](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/src/main/java/inspiaaa/micro8/Main.java) by changing the input path to your file (and if you do not want it to place the output files in the `examples/output` folder, then also change the output path).
3. Run the java program.

## 3. Loading a program into the computer

Since I built every component from scratch, the computer uses custom internal memory units made with basic logic gates. However, these memory units can't load binary data directly from a file. To solve this, I used external memory components in Digital that can accept binary files. These external components are only used to transfer data into the internal memory; they’re not involved in the actual execution process.

To load the data:

1. Open the `COMPUTER.dig` file.
2. Right-click on the component labelled `DATA` and select `Edit`. Then, from the `File/Load` menu, load the compiled data file.
3. Repeat the same process for the `INSTR` component.

## 4. Running the computer

1. Open the `COMPUTER.dig` file.
2. Start the simulation by pressing the play button in Digital.
3. To load the data and instructions into the internal memory units, click the `PROGRAM` button (this is the programming/flashing mode). This will iterate through all addresses and transfer the data from external memory into the internal memory. Note that the address counter may not be at 0 when programming starts. Since this computer uses memory-mapped I/O, you’ll see the screen clear at some point as it begins writing to these addresses.
4. Once all addresses have been programmed, turn off the programming mode by toggling the `PROGRAM` button.
5. The computer is now reset and running.
6. To manually control the clock, toggle the `CLK_MODE` switch and then generate the clock signal by toggling the `CLK` button.
7. To reset the computer, re-enable the `PROGRAM` mode. This will immediately reset the program counter and begin resetting the memory.

## Video showing steps 3 and 4

https://github.com/user-attachments/assets/fc253faf-d575-4dac-8814-a6c9ded96613

*[original video file](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Images/Tutorials/FlashingTutorial.mp4)*
