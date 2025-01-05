# Internal Control Signals

Overview of the internal control signals used in the control unit.

- `RSA1`: address of source register 1
- `RSA2`: address of source register 2
- `RDA`: address of the destination register
- `ALOP`: ALU operation
	- 000 = ADD
	- 001 = SUB
	- 010 = MUL
	- 011 = SLL
	- 100 = SRL
	- 101 = AND
	- 110 = OR
	- 111 = XOR
- `RWE`: destination register write enable
- `OP2Sel`: 2nd operand select
	- 0 = RS2
	- 1 = immediate
- `Im`: Decoded immediate, usually the 2nd operand input
- `RDSrc`: destination register result source select
	- 00 = ALU
	- 01 = Memory
	- 10 = PC+1
	- 11 = Immediate
- `MemWE`: memory write enable signal
- `PCNext`: next PC value select
	- 00 = PC + 1
	- 01 = immediate
	- 10 = RS1
	- 11 = branch target
- `CMPOP`: comparator operation
	- 00 = eq
	- 01 = neq
	- 10 = lt
	- 11 = ge