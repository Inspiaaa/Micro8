# Recursive fibonacci

.equ N, 5

# Move the stack pointer to high end.
li sp, 255

# Load parameters and call fib()
li x1, N
call fib


# n in x0
# return value in x1
fib:
    # if n <= 1:
    li x2, 1
    bgtu x1, x2, _normal
        # return 1
        li x1, 1
        ret

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

    # pop stack
    addi sp, 3
    ret
