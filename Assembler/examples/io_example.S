.text
li sp, STACK_END

li x1, IO

# Clear screen
li x4, 0
sb x4, OUT_0(x1)
sb x4, OUT_1(x1)
sb x4, OUT_2(x1)
sb x4, OUT_3(x1)
sb x4, OUT_4(x1)
sb x4, OUT_5(x1)

# Copy inputs to outputs
start:
lb x2, IN_A(x1)
lb x3, IN_B(x1)

sb x2, OUT_0(x1)
sb x3, OUT_1(x1)
j start