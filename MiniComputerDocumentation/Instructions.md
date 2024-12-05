- 8-bit architecture => Max 256 bytes of addressable RAM
- By keeping instructions and data in separate memory units and handling them separately (**Harvard architecture**), the amount of usable memory significantly increases. 256 bytes are then available solely for data, and 256 addresses available for instructions. As all instructions have the same size, the addresses do not have to refer to byte location, but can instead be used as indices, allowing for 256 * instruction size bytes of memory.

16 bit instructions. 8 general purpose registers (3 bit addresses).

Arithmetic and logic instructions:
- Format `op rd, rs1, rs2`
- `add`, `sub`, `mul`, `sll`, `srl`, ~~`sra`~~, `and`, `or`, `xor`

Immediate instructions
- Format `op rd/s, imm[8]`
- Source and destination registers are the same
- `li`, `addi`, `slli`, `srli`, `xori`, `andi`, `ori` (no `muli`, instead `li` for loading immediates)

Load / store instructions:
- `lb rd, uimm[5](rs)`
- `sb rs, uimm[5](rs)`

Jump instructions:
- `j imm[8]`
- `call imm[8]` (stores next instr. address in `ra`)
- `ret` (jumps to `ra`)

Branch instructions:
- `bxx rs1, rs2, imm[6]`
- `beq`, `bgtu`, `bltu`, `bgt`, `blt`

Move / copy instruction:
- `mv rd, rs`

```
|. . . . . . . . . . . . . . . .|
| rd  | rs1 | rs2 | op          |
| rsd | imm           | op      |
| rs1 | rs2 | imm         | op  |
```

| Registers |     |
| --------- | --- |
| x0        | sp  |
| x1        |     |
| x2        |     |
| x3        |     |
| x4        |     |
| x5        |     |
| x6        |     |
| x7        |     |
Additional register `ra` for return address (call / ret commands)
