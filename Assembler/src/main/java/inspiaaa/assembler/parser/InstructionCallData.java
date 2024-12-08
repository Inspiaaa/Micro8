package inspiaaa.assembler.parser;

import inspiaaa.assembler.Expression;

import java.util.List;

public class InstructionCallData {
    private final String name;
    private final List<Expression> arguments;
    private final int line;

    public InstructionCallData(String name, List<Expression> arguments, int line) {
        this.name = name;
        this.arguments = arguments;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public int getLine() {
        return line;
    }
}
