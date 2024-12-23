# Example program that computes the sum of all integers from 1 to N (PARAMETER_N).

.equ PARAMETER_N, 5

.data
.org 10
n:
    .byte PARAMETER_N

.text
start:
    li x0, n  # should be load address (la)

    li x1, 0  # Sum
    li x2, 1  # Counter

loop_start:
    # While counter (x2) <= n (x0)
    bltu x0, x2, end

    # Add counter to sum
    add x1, x1, x2

    # Increment counter
    addi x2, 1

    j loop_start

end:
    # Copy result to x0
    mv x0, x1
    nop