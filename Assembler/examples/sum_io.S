.text

# Wait for user input (wait for button in row B to be pressed, n should be in A).
wait_input:
li x6, IO
li x7, 0
li x4, 1
wait_input_start:
    lb x5, IN_B(x6)
    bne x5, x7, start

    # Animate dot
    sb x4, OUT_5(x6)
    sb x7, OUT_5(x6)
    j wait_input_start


start:
    # Show red bar during processing.
    li x7, 0b11111111
    sb x7, OUT_5(x6)

    # Compute sum
    lb x1, IN_A(x6)
    call compute_sum
    sb x1, OUT_0(x6)


# Hide the red bar.
li x7, 0
sb x7, OUT_5(x6)

# Wait for the user to clear the input field.
wait_no_input:
    lb x5, IN_B(x6)
    beq x5, x7, wait_input
    j wait_no_input


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

        # Animate.
        sb x2, OUT_0(x6)
        sb x3, OUT_1(x6)

        j _loop_start

    _return:
        # Clear second output row.
        li x3, 0
        sb x3, OUT_1(x6)

        # Copy result to x1
        mv x1, x2
        ret
