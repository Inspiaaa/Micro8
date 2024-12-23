start:
addi x0, -15
add sp, x0, x1
addi ra, x1
addi x7, 3

.equ variable, 10
.equ variable2, "hello"

# Comment

.org 20
addi x0, label

.org 30
label:
lb x0, 10(x1) # Comment

.org 40
addi x0, label