# Interactive maths program that allows you to input a number n and compute...
# - the sum of all natural numbers up to n
# - n factorial
# - the nth Fibonacci number.

# Options:
# 00000001  Sum
# 00000010  Factorial
# 00000100  Fibonacci

.data
sum_image:
.byte 0b00000000
.byte 0b00100000
.byte 0b01110000
.byte 0b00100000

factorial_image:
.byte 0b01000000
.byte 0b01000000
.byte 0b00000000
.byte 0b01000000

fibonacci_image:
.byte 0b11101010
.byte 0b10000010
.byte 0b11001011
.byte 0b10001011


.text

start:
# Wait for input
li x1, IO
li x2, 0
li x3, 1
wait_for_input:
    lb x4, IN_B(x1)
    bne x2, x4, decode_input
    # Animate
    sb x3, OUT_5(x1)
    sb x2, OUT_5(x1)
    j wait_for_input


decode_input:
lb x1, IN_B(x1)

li x2, 0b0000_0001
beq x1, x2, sum

li x2, 0b0000_0010
beq x1, x2, factorial

li x2, 0b0000_0100
beq x1, x2, fibonacci

# Invalid input
j start


sum:
la x1, sum_image
call show_image

li x1, IO
lb x1, IN_A(x1)
call compute_sum
j end

factorial:
la x1, factorial_image
call show_image

li x1, IO
lb x1, IN_A(x1)
call compute_factorial
j end

fibonacci:
la x1, fibonacci_image
call show_image

li x1, IO
lb x1, IN_A(x1)
call compute_fibonacci
j end


end:
call clear_lower_screen

# Wait for input field to be cleared.
li x1, IO
li x2, 0

wait_no_input:
    lb x3, IN_B(x1)
    beq x2, x3, start
    j wait_no_input


compute_sum:
    li x2, 0  # Counter
    li x3, 0  # Sum
    li x4, IO # IO

    _sum_loop:
        add x3, x3, x2

        sb x3, OUT_0(x4)
        sb x2, OUT_1(x4)

        addi x2, 1

        bgtu x2, x1, _sum_end
        j _sum_loop

    _sum_end:
    ret


compute_factorial:
    li x2, 1  # Counter
    li x3, 1  # Factorial
    li x4, IO # IO

    _fac_loop:
        mul x3, x3, x2

        sb x3, OUT_0(x4)
        sb x2, OUT_1(x4)

        addi x2, 1

        bgtu x2, x1, _fac_end
        j _fac_loop

    _fac_end:
    ret


compute_fibonacci:
    li x2, 1  # Counter
    li x3, 0  # fib(n-1), last
    li x4, 1  # fib(n), current
    li x5, IO # IO

    # if n == 0
    li x6, 0
    beq x1, x6, _fib_ret_0

    # if n == 1
    li x6, 1
    beq x1, x6, _fib_ret_1

    _fib_loop:
        beq x2, x1, _fib_end

        # next = current + last
        # last = current
        mv x6, x4
        add x4, x4, x3
        mv x3, x6

        sb x4, OUT_0(x5)
        sb x2, OUT_1(x5)

        addi x2, 1

        j _fib_loop

    _fib_end:
    ret

    _fib_ret_0:
    li x1, 0
    sb x1, OUT_0(x5)
    ret

    _fib_ret_1:
    li x1, 1
    sb x1, OUT_0(x5)
    ret

# Address of pixel rows in x1
show_image:
    li x2, IO

    lb x3, 0(x1)
    sb x3, OUT_2(x2)
    lb x3, 1(x1)
    sb x3, OUT_3(x2)
    lb x3, 2(x1)
    sb x3, OUT_4(x2)
    lb x3, 3(x1)
    sb x3, OUT_5(x2)

    ret


clear_lower_screen:
    li x1, IO
    li x2, 0

    sb x2, OUT_1(x1)
    sb x2, OUT_2(x1)
    sb x2, OUT_3(x1)
    sb x2, OUT_4(x1)
    sb x2, OUT_5(x1)
    ret