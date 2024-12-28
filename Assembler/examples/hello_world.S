.data
image:
    .byte 0b10010111
    .byte 0b10010100
    .byte 0b11110110
    .byte 0b10010100
    .byte 0b10010111

    .byte 0b01000100
    .byte 0b01000100
    .byte 0b01000100
    .byte 0b01000100
    .byte 0b01110111

    .byte 0b00110000
    .byte 0b01001000
    .byte 0b01001000
    .byte 0b01001000
    .byte 0b00110000

    .byte 0b01000100
    .byte 0b01000101
    .byte 0b01010101
    .byte 0b01101101
    .byte 0b01000100

    .byte 0b11001111
    .byte 0b00101001
    .byte 0b00101110
    .byte 0b00101010
    .byte 0b11001001

    .byte 0b01000111
    .byte 0b01000100
    .byte 0b01000100
    .byte 0b01000100
    .byte 0b01110111

    last_frame:
    .byte 0b00100000
    .byte 0b10100000
    .byte 0b10100000
    .byte 0b10000000
    .byte 0b00100000


# Start screen
.org IO
    .byte 0b11111111
    .byte 0b10000001
    .byte 0b10000001
    .byte 0b10000001
    .byte 0b10000001
    .byte 0b11111111


.text

start:
# Setup x8 for IO.
li x8, IO

# Clear the screen
li x1, 0
sb x1, OUT_0(x8)
sb x1, OUT_1(x8)
sb x1, OUT_2(x8)
sb x1, OUT_3(x8)
sb x1, OUT_4(x8)
sb x1, OUT_5(x8)

# Load first frame.
la x7, image  # current frame address

# For each frame:
draw_next_frame:
    li x2, 0  # column index

    draw_next_column:

    # Shift the frame left by one column.
    lb x1, OUT_0(x8)
    slli x1, 1
    sb x1, OUT_0(x8)

    lb x1, OUT_1(x8)
    slli x1, 1
    sb x1, OUT_1(x8)

    lb x1, OUT_2(x8)
    slli x1, 1
    sb x1, OUT_2(x8)

    lb x1, OUT_3(x8)
    slli x1, 1
    sb x1, OUT_3(x8)

    lb x1, OUT_4(x8)
    slli x1, 1
    sb x1, OUT_4(x8)

    # Load next column.
    # Idea: shift to left and then shift to right to isolate the next bit
    # and to ensure that it is at index 0 so that it can be trivially
    # added to the current pixel row.
    # Amount to shift left is x2 (same as current column index from left).

    # Next column
    lb x4, 0(x7)
    sll x4, x4, x2
    srli x4, 7
    lb x5, OUT_0(x8)
    or x5, x5, x4
    sb x5, OUT_0(x8)

    lb x4, 1(x7)
    sll x4, x4, x2
    srli x4, 7
    lb x5, OUT_1(x8)
    or x5, x5, x4
    sb x5, OUT_1(x8)

    lb x4, 2(x7)
    sll x4, x4, x2
    srli x4, 7
    lb x5, OUT_2(x8)
    or x5, x5, x4
    sb x5, OUT_2(x8)

    lb x4, 3(x7)
    sll x4, x4, x2
    srli x4, 7
    lb x5, OUT_3(x8)
    or x5, x5, x4
    sb x5, OUT_3(x8)

    lb x4, 4(x7)
    sll x4, x4, x2
    srli x4, 7
    lb x5, OUT_4(x8)
    or x5, x5, x4
    sb x5, OUT_4(x8)

    # Advance to the next column.
    addi x2, 1

    # Draw the next column.
    li x3, 8
    bltu x2, x3, draw_next_column

    # Last column reached. Draw the next frame.
    addi x7, 5
    la x3, last_frame
    bleu x7, x3, draw_next_frame

    # Final frame reached.
    la x7, image
    j draw_next_frame
