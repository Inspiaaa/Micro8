This document contains an overview of the available files and components in the Digital folder. Note that not all components were used in the final computer build.

## CPU

- `COMPUTER`: top-level component. Contains the computer interface with inputs, outputs, and logic for flashing / programming the memory units.
- `CPU`: actual processor with integrated memory. Contains input signals for loading data into the memory and inputs and outputs for IO.
- `CTRL_UNIT`: control unit responsible for instruction decoding and  generating the internal control signals. Manages the components in the `CPU`.

## Arithmetic

Binary operations (A OP B):
- `CRA_8`: Carry Ripple Adder
- `CSavA_8`: Carry Save Adder
- `CSavA_8_4_2`: Carry Save Adder from 4 inputs to 2 outputs.
- `CSA_8` / `CSA_4` / `CSA_2`: Conditional Sum Adder
- `SUB_8`: Subtractor (uses `ADD_8`)
- `ADD_SUB_8`: Adder / Subtractor (uses `ADD_8` internally)
- `MUL_8`: Multiplier
- `SLL_8`: Shift Left Logical (shift by `B[0:2]`)
- `SRL_8`: Shift Right Logical (shift by `B[0:2]`)
- `SRA_8`: Shift Right Arithmetic (shift by `B[0:2]`)
- `ADD_8`: Generic interface for the current fastest adder implementation

### Memory

- `SR_LATCH`: SR-Latch
- `D_LATCH`: D-Latch 
- `D_FLIPFLOP`: D-Flip-Flop
- `REGISTER_1`: 1 bit synchronous register 
- `REGISTER_8`: 8 bit synchronous register
- `64B_MEM`: 64 byte memory
- `256B_MEM`: 256 byte memory (consists of 4 `64B_MEM` units)
- `256_INSTR_MEM`: 256 instruction memory (512 bytes, consists of 2 `256B_MEM` units)
- `256B_MEM_IO`: 256 byte memory with memory-mapped IO
- `8_REGISTER_BANK`: Register File (8 registers, allowing reading from 2 registers and writing to 1 register) 
- `T_FF`: T-Flip-Flop
- `COUNTER_8`: 8-bit synchronous up counter (built using 8 T-flip-flops)

*synchronous = stores on rising edge of CLK signal*

### Comparison

- `CMPU_8`: unsigned integer comparator
- `CMPU_CTRL`: unsigned integer comparator unit that uses the ALU outputs (zero / carry) and selects the comparison based on the passed opcode.

`CMPU_CTRL` op encoding:

| OP  | Binary | Mnemonic | Function |
| --- | ------ | -------- | -------- |
| 0   | 00     | EQ       | `A = B`  |
| 1   | 01     | NE       | `A != B` |
| 2   | 10     | LT       | `A < B`  |
| 3   | 11     | GE       | `A >= B` |

## Foundational

- `HA`: Half Adder
- `FA`: Full Adder

### Utility

- `EXT_1_8`: duplicate a signal to an 8-bit output wire
- `GATE_8`: AND gate for an 8-bit input and a 1-bit enable signal
- `IS_ZERO_8`: check if 8-bit signal is zero
- `IS_EQU_8`: check if two 8-bit signals are equal 

## ALU

`ALU_8`

Inputs:
- Two 8-bit numbers

Outputs:
- 8-bit output
- Comparison flags obtained from SUB: is zero? / carry? (which are used in `CPU` by the `CMPU_CTRL` unit)

ALU op encoding:

| OP  | Binary | Function |              |
| --- | ------ | -------- | ------------ |
| 0   | 000    | ADD      | A + B        |
| 1   | 001    | SUB      | A - B        |
| 2   | 010    | MUL      | A * B        |
| 3   | 011    | SLL      | A << B[0:2]  |
| 4   | 100    | SRL      | A >>> B[0:2] |
| 5   | 101    | AND      | A & B        |
| 6   | 110    | OR       | A \| B       |
| 7   | 111    | XOR      | A ^ B        |
