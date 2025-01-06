# Assembly Reference

For an overview over the available instructions, see the *ISA* documentation.

## Overview

In general:
- (at most) one instruction or directive per line
- pseudo instructions and assembler directives are supported
- indentation (and most other whitespace) is insignificant

### Operand Types

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

`"Hello World! \" escaped quotes, \n etc."`

### Comments

Indicated by the `#` character.

```assembly
# Load value 20 into x1.
li x1, 20
add x1, x2, x3  # Add two numbers.
```

### Labels

- Labels can be used to specify the jump / branch target and are automatically converted to a relative / absolute address depending on the context.
- They can also be used between different memory banks to reference data / instruction addresses.
- To load the value of an address into a register, the `la rd, label` directive can be used.

Example:

```assembly
.data
parameter_n: .byte 4

.text
la x2, parameter_n
lb x1, 0(x2)
call sum_function

sum_function:
	li x2, 1
	li x3, 0
	loop_start:
		bgt x2, x1, end
		add x3, x3, x2
		addi x2, 1
		j loop_start
	end: ret
```

Note that labels can either be written on their own line or in front of an instruction. One line can also contain multiple labels.

| Directive      | Description                                                        |              |
| -------------- | ------------------------------------------------------------------ | ------------ |
| `la rd, label` | loads the absolute address of the label<br>into the given register | `rd = label` |

## Built-in Directives

### Memory Bank Directives

Switch between the main memory (data memory) and the instruction memory. Data directives and instructions after this directive will be written to the specified memory bank.
Note that the assembly language has no concept of sections.

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

| Name        | Value | Type    | Description                                                                                                     |
| ----------- | ----- | ------- | --------------------------------------------------------------------------------------------------------------- |
| `IO`        | `248` | address | start address of the memory-mapped IO                                                                           |
| `OUT_0`     | `0`   | number  | relative address of output row 0 from `IO`                                                                      |
| `OUT_1`     | `1`   | number  | ... row 1 ...                                                                                                   |
| `OUT_2`     | `2`   | number  | ... row 2 ...                                                                                                   |
| `OUT_3`     | `3`   | number  | ... row 3 ...                                                                                                   |
| `OUT_4`     | `4`   | number  | ... row 4 ...                                                                                                   |
| `OUT_5`     | `5`   | number  | ... row 5 ...                                                                                                   |
| `IN_A`      | `6`   | number  | ... input row `A`  ...                                                                                          |
| `IN_B`      | `7`   | number  | ... input row `B` ...                                                                                           |
| `STACK_END` | `247` | address | Highest possible address without interacting<br>with IO. Useful value for setting up the stack<br>pointer `sp`. |

Example:

```assembly
# Setup the stack.
la sp, STACK_END  # Use la instead of li to load an address.

# Prepare to read/write from IO.
la x1, IO

# Read from IO.
lb x2, IN_A(x1)

# Write to second output row.
sb x2, OUT_1(x1)
```

## Safe-Guards

The assembler has a rich warning system that can help prevent bugs.

### Example

Suspicious memory bank:

```
Warning --> examples/warnings.S:5:1 (14)
5 | add x1, x2, x3
    ^^^^^^^^^^^^^^
Writing instruction to data section. Use '.text' to switch to instruction section.

Warning --> examples/warnings.S:9:1 (9)
9 | .byte 100
    ^^^^^^^^^
Writing data to instruction section. Use '.data' to switch to data section.
```

Suspicious operands:

```
Warning --> examples/warnings.S:20:8 (3)
20 | .align 'a'
            ^^^
Expected a regular number, but received: CHAR('a')

Warning --> examples/warnings.S:23:3 (2)
23 | j 15
       ^^
Expected label, but received direct offset: NUMBER(15)

Warning --> examples/warnings.S:24:13 (2)
24 | bne x1, x2, 10
                 ^^
Expected label, but received direct offset: NUMBER(10)
```

Branch target too far away:

```
Warning --> examples/warnings.S:15:13 (6)
15 | beq x1, x2, target
                 ^^^^^^
Integer -215 is out of bounds for 7-bit signed integer.
```

Immediates out of bounds:

```
Warning --> examples/warnings.S:27:8 (3)
27 | li x1, 300
            ^^^
Integer 300 is out of bounds for 8-bit integer.

Warning --> examples/warnings.S:28:8 (4)
28 | li x1, -129
            ^^^^
Integer -129 is out of bounds for 8-bit integer.
```

Incorrect label usage:

```
Warning --> examples/warnings.S:35:3 (9)
35 | j my_struct
       ^^^^^^^^^
Using a data address as an instruction address.
```