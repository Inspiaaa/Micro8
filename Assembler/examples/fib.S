# Recursive fibonacci

.equ N, 5

li sp, 255
li x0, N
call fib

# n in x0
# return value in x0
fib:
    # if n <= 1:
    li x1, 1
    bgtu x0, x1, _normal
        # return 1
        li x0, 1
        ret

    _normal:
    # push n and ra on stack
    subi sp, 3
    sb x0, 0(sp)
    sb ra, 1(sp)

    # fib(n-1)
    subi x0, 1
    call fib

    # push fib(n-1) onto stack
    sb x0, 2(sp)

    # load n from stack
    lb x0, 0(sp)

    # fib(n-2)
    subi x0, 2
    call fib

    # load fib(n-1) from stack
    lb x1, 2(sp)

    # fib(n) = fib(n-1) + fib(n-2)
    add x0, x0, x1

    # pop stack
    addi sp, 3
    ret
