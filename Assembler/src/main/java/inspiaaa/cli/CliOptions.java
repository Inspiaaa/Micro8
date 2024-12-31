package inspiaaa.cli;

import java.util.HashMap;
import java.util.HashSet;

public class CliOptions {
    private final HashSet<String> flags = new HashSet<>();
    private final HashMap<String, String> values = new HashMap<>();

    void addFlag(String name) {
        flags.add(name);
    }

    void addValue(String name, String value) {
        values.put(name, value);
    }

    public boolean isFlagSet(String name) {
        return flags.contains(name);
    }

    public boolean hasOption(String name) {
        return values.containsKey(name);
    }

    public String get(String name) {
        return values.get(name);
    }
}
