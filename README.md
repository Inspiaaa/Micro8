# Micro 8

![](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Images/Animations/HelloWorld.gif)

## What is this project?

- A custom 8-bit RISC **instruction set architecture** (ISA) similar to RISC-V.
- A **computer built from scratch** that implements this ISA, designed using the digital logic simulation software [Digital](https://github.com/hneemann/Digital).
- A **general-purpose assembler** that can be used for similar projects.
- An assembler specifically designed for this computer, built on this framework.
- Example assembly programs for demonstrating the computer's capabilities.

This document not only explains how this computer was designed and constructed, but also outlines how you can create an assembler for your own computer designs. See the *Assembler* section.

# Computer

## Overview

- **8-bit RISC** architecture
- **Harvard architecture**: 
	- 256 bytes of data memory
	- 512 bytes of instruction memory (16-bit instructions)
- 8 general-purpose registers
- All components are **built from the ground up** using basic digital elements (wires, logic gates, multiplexers), including memory and multiplication units
- **Memory-mapped I/O**
- Efficient implementation:
	- **Conditional sum adder** for fast addition
	- **Multiplier** using **carry-save adders** and a tree-based structure
	- Optimized right/left **shifters**
- More than 66,000 nodes in the design

For more information on the ISA and the design choices that went into it, please see the [ISA documentation](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/ISA.md). Moreover, the [documentation folder](https://github.com/Inspiaaa/Micro8/tree/master/Micro8Documentation) contains further information on the internal design.

![](https://github.com/Inspiaaa/Micro8/raw/master/Micro8Documentation/Images/Components/MEM_64.png)

*For **pictures and screenshots** of the circuits and components, see [this page](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Screenshots.md).*

## Usage

1. Install [Digital](https://github.com/hneemann/Digital).
2. Download the `Digital` folder from this repo.
3. Open the `COMPUTER.dig` file in Digital.
4. See the [guide](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Usage%20Tutorial.md) (text and video tutorial) on how to run the computer and how to load your own assembly files.

![](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Images/Animations/Maths.gif)

*Example program that allows you to perform maths. The first operation calculates the sum of all positive integers up to n (the given number in the upper row). The second operation calculates the factorial of n and the third iteratively computes the n-th Fibonacci number.*

# Assembler

## Features

- A **general-purpose assembler** framework that can be easily adapted for use in custom projects.
- Support for **assembler directives** and **pseudo-instructions**.
- **Instruction overloading** based on parameter count and type-
- Built-in **assembler directives** for storing data in memory (such as strings, bytes, shorts, etc.).
- Built-in **layout directives** such as `.org` and `.align`.
- Support for **user-defined variables**.
- **Arbitrary data bit widths**: Configure memory cells, data directives, and custom word sizes (e.g., memory cells can be 20 bits wide).
- Useful **error and warning messages** with clear and accurate code locations.
- **Static analysis** and **warning system** to catch potential issues ahead of time.
- **Custom memory banks**: Configure as many memory banks as needed, each with customizable sizes and word bit widths (not limited to just instruction and data memory).
- Support for **varied register types**.
- **Fine-grained control** over output generation and instruction encoding.

## Usage

Copy `inspiaaa.assembler` package (path: `Assembler/src/main/java/inspiaaa/assembler`) into your own project.

### Learn from the example

You can use the existing `micro8` assembler as an example. It includes a comprehensive setup, including:
- Custom instructions
- Pseudo instructions
- Directives
- Static analysis (e.g. label checking)
- Two memory banks with different cell sizes

**Where to start:**
- The main definition of the assembler: [`Micro8Assembler.java`](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/src/main/java/inspiaaa/micro8/Micro8Assembler.java)
- Usage of the assembler and output generation: [`Main.java`](https://github.com/Inspiaaa/Micro8/blob/master/Assembler/src/main/java/inspiaaa/micro8/Main.java)

### Creating your own assembler

**The general process is as follows:**
- Create an `Assembler` instance.
- Define the available registers, constants and built-in directives.
- Create custom instruction types and pseuo-instructions, and add them to the assembler.

Once this setup is complete, you can begin inputting assembly programs into the assembler. The result will be the data stored in the memory banks, which can then be extracted and saved in the desired format.

Optionally, you can create a CLI interface. The example assembler includes a simple, custom CLI parser with zero dependencies, but for more robust functionality, it's recommended to use an established library.

## Concepts

### Instruction

The `Instruction` class is a core component of the assembler. It represents a wide range of elements, including regular instructions, pseudo-instructions, and assembler directives (with labels implemented as directives).

Each `Instruction` object describes a recipe for how to process an instruction / directive written in the input program. It can modify the state of the assembler, e.g. by changing the current address counter, or write data to the output memory.

When the input program is read, it is translated to a sequence of `InstructionCall` objects that are then bound to the corresponding `Instruction` instance.

**Methods and execution order:**
1. `assignAddress`: Assign an address to the current instruction or modify the address counter (e.g., for `.org` and `.align` directives).
2. `preprocess`: Can be used to add a symbol to the symbol table.
3. `validate`: Perform static analysis on the given instruction to check for correctness.
4. `compile`: If applicable, encode the instruction to binary and write it to memory.

### Pseudo-Instructions

Pseudo-instructions are also represented by the `Instruction` class, but implement the `Lowerable` interface.

The `Lowerable` interface provides a method that translates the pseudo-instruction into other (lower-level) instructions. These may also be pseudo-instructions, as the assembler recursively lowers instructions until only normal instructions remain.

Instructions are lowered before the aforementioned methods (`assignAddress`, `preprocess`, ...) are executed.

### Location

An important class used throughout the entire assembler is the `Location` class. It represents a code location and is used to output accurate error and warning messages. 

Most types in the assembler carry location information, such as the `Expr` types or `InstrucionCall` objects.

Example output enabled by storing location information:

```
Warning --> examples/warnings.S:28:8 (4)
28 | li x1, -129
            ^^^^
Integer -129 is out of bounds for 8-bit integer.
```

### Expression

Values are represented by the `Expr` class, with each operand being an instance of `Expr`. Subclasses implement different types, such as strings, characters, and more.

Since integer types are the most common, they receive special handling: the `isNumeric()` method is used instead of `instanceof` checks, and `getNumericValue()` is provided for easy access.

When a symbol is used in place of a direct value, its corresponding value usually needs to be looked up. For numeric types, this lookup happens automatically in the symbol table. However, for other types, you must remove the indirection using the `unwrap()` method manually, which recursively resolve symbols.


### Overloading and Type Checking

The assembler supports overloading based on operand types. Each instruction defines a list of parameter types, which are checked using parameter type checkers (see the `ParameterType` interface). This system allows you to implement a potentially complex set of rules that determine which values are accepted and which are incompatible. 

In addition to validating whether a value is legal, parameter type checkers also help identify potentially incorrect or suspicious values during static analysis.

Example of a potentially erroneous, but legal value:

```
Warning --> examples/warnings.S:24:13 (2)
24 | bne x1, x2, 10
                 ^^
Expected label, but received direct offset: NUMBER(10)
```

### Registers

The assembler has no direct register type. Instead, it employs a more general and flexible concept: **distinct integers**. These consist of a type ID and an integer value, where the type represents the register type, and the value serves as the specific register's ID.

Different register types, such as integer and floating-point registers, can be implemented similarly by assigning different `type` values.

Distinct integers are provided by the assembler and can only be referenced in the user program; they cannot be created. They are stored in the assembler’s symbol table as constants.

It is also worth mentioning that although distinct integers have a numeric value (see `getNumericValue()`), the `isNumeric()` method returns false. This is because they only have an internal numeric representation that should  not be used as a regular number in the assembly program.

### Memory

The memory stores the binary representations of both the assembled instructions and the data generated by directives.

You can define multiple memory banks, such as separate instruction and data memories, with as many banks as needed. Each bank can have a unique size (number of addressable cells) and a fixed cell size (number of bits per address).

When writing a value to memory and the memory is visualized as a bitstream, the least significant bit of the number comes first, following a little-endian format when the value spans multiple cells / addresses.