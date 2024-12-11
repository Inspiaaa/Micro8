## Overview

- 8-bit RISC computer (8 bit addresses, 8 bit ALU, 8 bit registers, ...)
- Harvard architecture
- Single Cycle processor

**Instructions:**
- 16 bit, fixed-length instructions
- Inspired by RISC-V, subset of commands

**Memory:**
- Main memory: 256 bytes of addressable RAM
- Instruction memory (ROM): 256 instructions, each 16 bit (internally: 512 bytes of memory)

> [!INFO] Design Choice
> By keeping instructions and data in separate memory units and handling them separately (**Harvard architecture**), the amount of usable memory significantly increases. 256 bytes are then available solely for data, and 256 addresses available for instructions. As all instructions have the same size, the addresses do not have to refer to byte location, but can instead be used as indices, allowing for (256 * instruction size) bytes of instruction memory.
> 
> 16-bit instruction width was chosen...
> - ... as a personal challenge.
> - ... as a good compromise between flexibility and memory consumption.

**Registers:**
- 8 general-purpose registers (3 bit addresses)
- `x0`, `x1`, `x2`, ..., `x7`
- Convention:
	- `x7` can be used as the stack pointer (`sp`)
	- `x6` can be used as the return address (`ra`)


> [!INFO] Design Choice
> - Only 8 registers in order to fit into 16 bit instruction format.
> - In order to make the most of these registers, there is no zero register like in RISC-V: The instructions were specifically designed and chosen so that a hardwired zero register is not needed. E.g. mv rd, rs, j and jal implemented separately, ...

## Instructions

Arithmetic and logic instructions:
- Format `op rd, rs1, rs2`
- `add`, `sub`, `mul`, `sll`, `srl`, ~~`sra`~~, `and`, `or`, `xor`

Arithmetic and logic instructions with 
- Format `op rd/s, imm[8]`
- Source and destination registers are the same
- `addi`, `subi`, `muli`, `slli`, `srli`, `andi`, `ori`, `xori`

Load / store instructions:
- `lb rd, uimm[5](rs)`
- `sb rs2, uimm[5](rs1)`

Jump instructions:
- Jump: `j imm[8]`
- Jump and link: `jal rd, imm[8]` (stores next instr. address in `rd`)
- Jump register: `jr rs`
- Jump and link register: `jal rd, rs` (stores next instr. address in `rd`)

Branch instructions:
- `bxx rs1, rs2, imm[6]`
- `beq`, `bne`, `bgeu`, `bltu`

Move / copy instruction:
- `mv rd, rs`

Load constant:
- `li rd, imm[8]`

### Pseudo Instructions

| Pseudo               | Translation           |
| -------------------- | --------------------- |
| `call label`         | `jal ra, label`       |
| `ret`                | `jr ra`               |
| `ret rs`             | `jr rs`               |
| `bleu rs1, rs2, imm` | `bgeu rs2, rs1, imm`  |
| `bgtu rs1, rs2, imm` | `bltu rs2, rs1, imm`  |
| `not rd`             | `xori rd, 0b11111111` |
