For an overview over the available instructions, see the *ISA* documentation.

## Overview

In general:
- one instruction per line
- pseudo instructions and assembler directives are supported
- indentation (and most other whitespace) is insignificant

### Operand Data Types

- Registers
- Numbers (immediates)
- Register-relative addresses
- Strings (only used by the assembler directives)

#### Registers

| Name | Synonym |
| ---- | ------- |
| `x1` |         |
| `x2` |         |
| `x3` |         |
| `x4` |         |
| `x5` |         |
| `x6` |         |
| `x7` | `ra`    |
| `x8` | `sp`    |

#### Number Literals

- **binary**: `0b1001`, `-0b110`, `+0b1010`
- **hexadecimal**: `0xa10`, `0xFF3`, `-0x123`
- **decimal**: `10`, `-15`, `+23`
- **characters** (ASCII encoded): `'a'`, `'A'`, `'\n'`

Number literals also support separators to enhance readability, e.g. `0b1001_1101`, `0xAAB_1F0`.

#### Register-Relative Addresses

Format: `immediate(register)` represents the memory address `[register + immediate]`. 
E.g. `5(x1)`

#### Strings

`"This is a string. \" escaped quotes. \n etc."`

### Comments

Indicated by the `#` character.

```assembly
# Load value 20 into x1.
li x1, 20
add x1, x2, x3  # Add two numbers.
```

### Labels

TODO
Labels (own line, in-line, multiple in line), also from different memory banks (usage: jump, branch, `la` directive)
Error messages
Warning messages

## Built-in Directives

### Memory Bank Directives

Switch between the main memory (data memory) and the instruction memory. Data directives / instructions after this directive will be written to the specified memory bank. 

| Directive | Description                      |
| --------- | -------------------------------- |
| `.text`   | switch to the instruction memory |
| `.data`   | switch to the data memory        |

Example:

```assembly
.data
.byte 1, 2, 3

.text
addi x1, 2
```

### Memory Layout Directives

Modify the address of the next instruction / data directive.

| Directive    | Description                                                                                                                                              |
| ------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `.org num`   | sets the address of the next instruction / data directive<br>in the current memory bank to the given value                                               |
| `.align num` | aligns the address of the next instruction / data directive<br>to the next higher multiple of `num` (stays at the current<br>address if already aligned) |

Example:

```assembly
.org 100

.byte 1  # Address 100

.align 4
.byte 1  # Address would normally be 101, but is now 104.
```

### Data Directives

Write data directly to the current memory bank.

| Directive             | Description                                                                                          |
| --------------------- | ---------------------------------------------------------------------------------------------------- |
| `.byte num[8], ...`   | writes one byte for each number                                                                      |
| `.2byte num[16] ...`  | writes two bytes for each number,<br>unaligned                                                       |
| `.4byte num[32], ...` | writes four bytes for each number,<br>unaligned                                                      |
| `.8byte num[64], ...` | writes eight bytes for each number,<br>unaligned                                                     |
| `.half num[16], ... ` | writes two bytes for each number,<br>naturally aligned                                               |
| `.word num[32], ...`  | writes four bytes for each number,<br>naturally aligned                                              |
| `.dword num[64], ...` | writes eight bytes for each number,<br>naturally aligned                                             |
| `.zero nbytes`        | zeroes the next `nbytes`                                                                             |
| `.ascii "..."`        | writes a string encoded in ASCII                                                                     |
| `.asciz "..."`        | writes a string encoded in ASCIII <br>and automatically adds a 0 (c-style) <br>delimiter at the end. |

Notes:
- Multi-byte integers are written in little-endian order.
- If the cell size of the current bank is greater than the data to write, the remaining bits are zeroed. The address of the next instruction is still incremented (as there are no fractional addresses).
- If the cell size is smaller than the data to write, then the value spans multiple addresses (with little endian ordering)

## Built-in Constants

| Name        | Value | Description                                                                                                     |
| ----------- | ----- | --------------------------------------------------------------------------------------------------------------- |
| `IO`        | `248` | start address of the memory-mapped IO                                                                           |
| `OUT_0`     | `0`   | relative address of output row 0 from `IO`                                                                      |
| `OUT_1`     | `1`   | ... row 1 ...                                                                                                   |
| `OUT_2`     | `2`   | ... row 2 ...                                                                                                   |
| `OUT_3`     | `3`   | ... row 3 ...                                                                                                   |
| `OUT_4`     | `4`   | ... row 4 ...                                                                                                   |
| `OUT_5`     | `5`   | ... row 5 ...                                                                                                   |
| `IN_A`      | `6`   | ... input row `A`  ...                                                                                          |
| `IN_B`      | `7`   | ... input row `B` ...                                                                                           |
| `STACK_END` | `247` | Highest possible address without interacting<br>with IO. Useful value for setting up the stack<br>pointer `sp`. |

Example:

```assembly
# Setup the stack
li sp, STACK_END

# Prepare to read/write from IO
li x1, IO

# Read from IO
lb x2, IN_A(x1)

# Write to second output row
sb x2, OUT_1(x1)
```