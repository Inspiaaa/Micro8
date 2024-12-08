package inspiaaa.assembler;

import java.util.List;

@FunctionalInterface
public interface InstructionFactory {
    Instruction convert(List<Expression> arguments, int line);
}
