# Recursive fibonacci

.equ N, 8

# Move the stack pointer to high end.
# Note that you should normally use the STACK_END value so that the stack does
# not interfere with the memory mapped IO, but in this example I wanted to
# intentionally visualize the stack on the screen during processing.
li sp, 253

# Load parameters and call fib()
li x1, N
call fib

# Clear the screen
li x2, IO
li x3, 0
sb x3, OUT_1(x2)
sb x3, OUT_2(x2)
sb x3, OUT_3(x2)
sb x3, OUT_4(x2)
sb x3, OUT_5(x2)

# Display result on the screen
sb x1, OUT_0(x2)

# Stop
stop: j stop


# n in x1
# return value in x1
fib:
    # if n == 0: return 0
    li x2, 0
    beq x1, x2, _ret_x1  # return 0

    # if n == 1: return 1
    li x2, 1
    beq x1, x2, _ret_x1  # return 1

    _normal:
    # push n and ra on stack
    subi sp, 3
    sb x1, 0(sp)
    sb ra, 1(sp)

    # fib(n-1)
    subi x1, 1
    call fib

    # push fib(n-1) onto stack
    sb x1, 2(sp)

    # load n from stack
    lb x1, 0(sp)

    # fib(n-2)
    subi x1, 2
    call fib

    # load fib(n-1) from stack
    lb x2, 2(sp)

    # fib(n) = fib(n-1) + fib(n-2)
    add x1, x1, x2

    # load ra from stack
    lb ra, 1(sp)

    # pop stack
    addi sp, 3
    ret

    _ret_x1:
    ret
