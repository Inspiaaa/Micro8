# Example program that produces many warnings.

# Instruction in data memory.
.data
add x1, x2, x3

# Data in instruction memory.
.text
.byte 100

# Branch target too far away.
.org 0
target: nop
.org 215
beq x1, x2, target

.org 0

# Operand suspicious: immediate as address.
j 15

# Operand suspicious: immediate passed as a register.
add x1, x2, 3

# Value out of range.
li x1, 300
li x1, -129

# Instruction address in data memory.
.data
my_struct:
.byte 20
.text
j my_struct
