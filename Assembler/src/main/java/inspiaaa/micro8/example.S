start:
label: addi x0, -15
add sp, x0, x1
addi ra, x1
addi x7, 3

# Comment

lb x0, 10(x1) # Comment
.org 0x50
addi ra, x1

.data
.byte 1, 2, 3, 4, 100, 300, 500