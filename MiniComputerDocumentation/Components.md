8 bit computer

### Arithmetic

Binary (A OP B)
- Carry Ripple Adder: `CRA_8`
- Carry Save Adder: `CSavA_8`
- Conditional Sum Adder: `CSA_8` / `CSA_4` / `CSA_2`
- Subtractor: `SUB_8` (uses `ADD_8`)
- Add / Subtractor: `ADD_SUB_8` (uses `ADD_8`)
- Multiplicator: `MUL_8`
- Shift Left Logical: `SLL_8` (shift by B[0:3])
- Shift Right Logical: `SRL_8` (shift by B[0:3])
- Shift Right Arithmetic: `SRA_8` (shift by B[0:3])
- Generic interface for current fastest adder implementation: `ADD_8`

### Memory

- SR-Latch: `SR_LATCH`
- D-Latch: `D_LATCH`
- D-Flip-Flop: `D_FLIPFLOP`
- 1 bit synchronous register: `REGISTER_1` (stores on rising edge of CLK signal)
- 8 bit synchronous register: `REGISTER_1` (stores on rising edge of CLK signal)

### Comparison

- Unsigned Integer Comparator: `CMPU_8`

### Foundational

- Half Adder: `HA`
- Full Adder: `FA`

### Utility

- Duplicate signal to 8 wire output: `EXT_1_8`

### ALU

- Supported operations:
	0. ADD
	1. SUB
	2. MUL
	3. SLL
	4. SRL
	5. SRA
	6. AND
	7. OR
	8. NAND
	9. NOR
	10. XOR
	11. NOT