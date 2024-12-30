package inspiaaa.micro8;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.LabelExpr;

public class StaticAnalysis {
    public static void ensureIsInDataBank(InstructionCall instruction, ErrorReporter er) {
        if (!instruction.getAddress().getBankId().equals(Micro8Assembler.DATA_BANK)) {
            er.reportWarning(
                    "Writing data to instruction section. Use '.data' to switch to data section.",
                    instruction.getLocation());
        }
    }

    public static void ensureIsInstructionAddress(Expr address, ErrorReporter er) {
        address = address.unwrap();

        if (!(address instanceof LabelExpr label)) {
            return;
        }

        if (!label.getAddress().getBankId().equals(Micro8Assembler.INSTRUCTION_BANK)) {
            er.reportWarning(
                    "Using a data address as an instruction address.",
                    address.getLocation());
        }
    }
}
