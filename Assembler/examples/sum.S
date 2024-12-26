# Example program that computes the sum of all integers from 1 to N (PARAMETER_N).

.equ PARAMETER_N, 5

.data
.org 10
n:
    .byte PARAMETER_N


.text
start:
    la x1, n
    lb x1, 0(x1)

    call compute_sum
    nop


# Pass n in x1.
compute_sum:
    li x2, 0  # Sum
    li x3, 1  # Counter

    _loop_start:
        # While counter (x3) <= n (x1)
        bgtu x3, x1, _return

        # Add counter to sum
        add x2, x2, x3

        # Increment counter
        addi x3, 1

        j _loop_start

    _return:
        # Copy result to x1
        mv x1, x2
        ret
