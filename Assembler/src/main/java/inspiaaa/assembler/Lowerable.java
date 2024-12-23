package inspiaaa.assembler;

import java.util.List;

public interface Lowerable {
    List<InstructionCall> lower(InstructionCall instruction);
}
