start:
label: addi x0, -15
add sp, x0, x1
addi ra, x1
addi x7, 3

.equ variable, 10
.equ variable2, "hello"
.equ variable3, add

# Comment

lb x0, 10(x1) # Comment
.org 0x50
addi variable3, variable2
