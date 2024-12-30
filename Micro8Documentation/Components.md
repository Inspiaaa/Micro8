8-bit architecture

### CPU

- Computer interface with inputs, outputs, and logic for flashing / programming the memory units: `COMPUTER`
- Actual CPU with integrated memory: `CPU`
- Control unit responsible for instruction decoding and  generating the internal control signals: `CTRL_UNIT`

### Arithmetic

Binary (A OP B)
- Carry Ripple Adder: `CRA_8`
- Carry Save Adder: `CSavA_8`
- Carry Save Adder from 4 inputs to 2 outputs: `CSavA_8_4_2`
- Conditional Sum Adder: `CSA_8` / `CSA_4` / `CSA_2`
- Subtractor: `SUB_8` (uses `ADD_8`)
- Add / Subtractor: `ADD_SUB_8` (uses `ADD_8`)
- Multiplicator: `MUL_8`
- Shift Left Logical: `SLL_8` (shift by B[0:2])
- Shift Right Logical: `SRL_8` (shift by B[0:2])
- Shift Right Arithmetic: `SRA_8` (shift by B[0:2])
- Generic interface for current fastest adder implementation: `ADD_8`

### Memory

- SR-Latch: `SR_LATCH`
- D-Latch: `D_LATCH`
- D-Flip-Flop: `D_FLIPFLOP`
- 1 bit synchronous register: `REGISTER_1`
- 8 bit synchronous register: `REGISTER_8`
- 64 byte memory: `64B_MEM`
- 256 byte memory: `256B_MEM` (consists of 4 `64B_MEM` units)
- 256 instruction memory (512 bytes): `256_INSTR_MEM` (consists of 2 `256B_MEM` units)
- 256 byte memory with memory-mapped IO: `256B_MEM_IO`
- Register File (8 registers, allowing reading from 2 registers and writing to 1 register): `8_REGISTER_BANK` 

*synchronous = stores on rising edge of CLK signal*

### Comparison

- Unsigned Integer Comparator: `CMPU_8`
- Unsigned Integer Comparator that uses the ALU outputs (zero / carry): `CMPU_CTRL`

### Foundational

- Half Adder: `HA`
- Full Adder: `FA`

### Utility

- Duplicate signal to 8 wire output: `EXT_1_8`
- And gate for an 8 bit input and 1 bit enable signal: `GATE_8`
- Check if zero signal is zero: `IS_ZERO_8`
- Check if two 8-bit signals are equal: `IS_EQU_8`

### ALU

- Two 8-bit inputs
- One 8-bit output + comparison flags from SUB: is zero? / carry?

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
