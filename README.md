# Micro 8

![](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/Images/Animations/HelloWorld.gif)

## What is this project?

- A custom 8-bit RISC **instruction set architecture** (ISA) similar to RISC-V.
- A **computer built from scratch** that implements this ISA, designed using the digital logic simulation software [Digital](https://github.com/hneemann/Digital).
- A **general-purpose assembler** that can be used for similar projects.
- An assembler specifically built for this computer, built on top of this framework.
- Example assembly programs for demonstrating the computer's capabilities.

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

For more information on the ISA and the design choices that went into it, please see the [ISA documentation](https://github.com/Inspiaaa/Micro8/blob/master/Micro8Documentation/ISA.md).

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
