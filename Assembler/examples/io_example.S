# Example program that shows simple interactions with the IO.

.text
li sp, STACK_END

la x1, IO

# Clear screen
li x4, 0
sb x4, OUT_0(x1)
sb x4, OUT_1(x1)
sb x4, OUT_2(x1)
sb x4, OUT_3(x1)
sb x4, OUT_4(x1)
sb x4, OUT_5(x1)

# Wait until any button in the B row is pressed
li x3, 0
li x4, 0
wait:
    lb x2, IN_B(x1)

    addi x4, 1
    sb x4, OUT_2(x1)

    bne x2, x3, start
    j wait

# Copy inputs to outputs
start:
    lb x2, IN_A(x1)
    lb x3, IN_B(x1)

    sb x2, OUT_0(x1)
    sb x3, OUT_1(x1)
j start