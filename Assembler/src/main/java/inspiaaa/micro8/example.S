# Example program that computes the sum of all integers from 1 to N (PARAMETER_N).

# TODO: Move code into function

.equ PARAMETER_N, 5

.data
.org 10
n:
    .byte PARAMETER_N


.text
start:
    la x0, n
    lb x0, 0(x0)

    call compute_sum
    nop


# Pass n in x0.
compute_sum:
    li x1, 0  # Sum
    li x2, 1  # Counter

    _loop_start:
        # While counter (x2) <= n (x0)
        bgtu x2, x0, _return

        # Add counter to sum
        add x1, x1, x2

        # Increment counter
        addi x2, 1

        j _loop_start

    _return:
        # Copy result to x0
        mv x0, x1
        ret
