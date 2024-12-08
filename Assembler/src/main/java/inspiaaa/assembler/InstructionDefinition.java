package inspiaaa.assembler;

import inspiaaa.assembler.parser.InstructionCallData;

import java.util.List;

public class InstructionDefinition {
    private String name;
    private ParameterType[] parameters;
    private InstructionFactory factory;
    private int parameterCount;

    public InstructionDefinition(String name, ParameterType[] parameters, InstructionFactory factory) {
        this.name = name;
        this.parameters = parameters;
        this.factory = factory;

        for (ParameterType param : parameters) {
            if (param == ParameterType.RELATIVE_ADDRESS) {
                parameterCount += 2;
            } else {
                parameterCount += 1;
            }
        }
    }

    public Instruction tryConvert(InstructionCallData call) {
        String name = call.getName();
        List<Expression> arguments = call.getArguments();

        if (!this.name.equals(name)) {
            return null;
        }

        if (arguments.size() != parameterCount) {
            return null;
        }

        return factory.convert(arguments, call.getLine());
    }

    public String getName() {
        return name;
    }

    public ParameterType[] getParameters() {
        return parameters;
    }

    public int getParameterCount() {
        return parameterCount;
    }

    @Override
    public String toString() {
        if (parameters.length == 0) {
            return name;
        }

        var string = new StringBuilder();

        string.append(name);
        string.append(" ");
        string.append(parameters[0]);

        for (int i = 1; i < parameters.length; i ++) {
            string.append(", ");
            string.append(parameters[i]);
        }

        return string.toString();
    }
}
