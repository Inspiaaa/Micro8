package inspiaaa.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CliParser {
    private final List<String> positionalArgumentNames = new ArrayList<>();
    private final HashMap<String, String> flagShortNameToName = new HashMap<>();
    private final HashSet<String> flagNames = new HashSet<>();
    private final HashMap<String, String> optionShortNameToName = new HashMap<>();
    private final HashSet<String> optionNames = new HashSet<>();
    private final HashSet<String> reservedNames = new HashSet<>();

    private final String helpMessage;

    public CliParser(String helpMessage) {
        this.helpMessage = helpMessage;
        addFlag("-h", "--help");
    }

    private void reserveName(String name) {
        if (reservedNames.contains(name)) {
            throw new IllegalArgumentException("Name '" + name + "' is already taken.");
        }
        reservedNames.add(name);
    }

    // Positional argument.
    public void addArgument(String name) {
        reserveName(name);
        positionalArgumentNames.add(name);
    }

    // Optional argument.
    public void addFlag(String longName) {
        longName = parseOptionName(longName);
        reserveName(longName);

        flagNames.add(longName);
    }

    // Optional argument.
    public void addFlag(String shortName, String longName) {
        shortName = parseOptionShortName(shortName);
        longName = parseOptionName(longName);
        reserveName(shortName);
        reserveName(longName);

        flagNames.add(longName);
        flagShortNameToName.put(shortName, longName);
    }

    // Optional argument.
    public void addOption(String longName) {
        longName = parseOptionName(longName);
        reserveName(longName);

        optionNames.add(longName);
    }

    public void addOption(String shortName, String longName) {
        shortName = parseOptionShortName(shortName);
        longName = parseOptionName(longName);
        reserveName(shortName);
        reserveName(longName);

        optionNames.add(longName);
        optionShortNameToName.put(shortName, longName);
    }

    private String parseOptionName(String name) {
        ensureIsValidOptionName(name);
        return name.replaceFirst("--", "");
    }

    private String parseOptionShortName(String name) {
        ensureIsValidShortOptionName(name);
        return name.replaceFirst("-", "");
    }

    private void ensureIsValidShortOptionName(String name) {
        if (!name.startsWith("-")) {
            throw new IllegalArgumentException("Short name must start with '-'");
        }
        if (name.length() != 2) {
            throw new IllegalArgumentException("Short name must consist of exactly one character.");
        }
    }

    private void ensureIsValidOptionName(String name) {
        if (!name.startsWith("--")) {
            throw new IllegalArgumentException("Option name must start with '--'");
        }
        if (name.length() <= 2) {
            throw new IllegalArgumentException("Long name must have at least one character.");
        }
    }

    public CliOptions parse(String[] args) {
        CliOptions result = new CliOptions();
        int positionalArgumentCount = 0;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            boolean hasNextArgument = i < args.length-1;

            // Positional argument.
            if (!arg.startsWith("-")) {
                if (positionalArgumentCount >= positionalArgumentNames.size()) {
                    reportTooManyPositionalArguments();
                }
                result.addValue(positionalArgumentNames.get(positionalArgumentCount++), arg);
                continue;
            }

            String name = arg.replaceFirst("^--?", "");

            if (arg.startsWith("---")) {
                throw new IllegalArgumentException("Invalid argument syntax: '" + arg + "'");
            }

            // Long-form option or flag.
            if (arg.startsWith("--")) {
                // Flag
                if (flagNames.contains(name)) {
                    result.addFlag(name);
                    continue;
                }

                // Option
                if (optionNames.contains(name)) {
                    if (!hasNextArgument) {
                        reportMissingOptionValue(name);
                    }
                    result.addValue(name, args[++i]);
                }

                reportUnknownOption(arg);
            }

            // Short-form flag.
            if (flagShortNameToName.containsKey(name)) {
                result.addFlag(flagShortNameToName.get(name));
                continue;
            }

            // Short-form option.
            if (optionShortNameToName.containsKey(name)) {
                String longName = optionShortNameToName.get(name);
                if (!hasNextArgument) {
                    reportMissingOptionValue(name);
                }
                result.addValue(longName, args[++i]);
                continue;
            }

            // Multiple short-form flags.
            parseShortFormFlagComposite(name, result);
        }

        if (result.isFlagSet("help")) {
            printHelp();
        }

        if (positionalArgumentCount < positionalArgumentNames.size()) {
            reportTooFewPositionalArguments();
        }

        return result;
    }

    private void parseShortFormFlagComposite(String options, CliOptions result) {
        for (int c = 0; c < options.length(); c++) {
            String flag = Character.toString(options.charAt(c));

            if (!flagShortNameToName.containsKey(flag)) {
                reportNotFlagInShortFormComposite(flag, options);
            }

            result.addFlag(flagShortNameToName.get(flag));
        }
    }

    private void printHelp() {
        System.out.println(helpMessage);
        System.exit(0);
    }

    private void reportUnknownOption(String arg) {
        throw new IllegalArgumentException("Unknown argument '" + arg + "'.");
    }

    private void reportMissingOptionValue(String arg) {
        throw new IllegalArgumentException("Argument value missing for option '" + arg + "'.");
    }

    private void reportTooManyPositionalArguments() {
        throw new IllegalArgumentException("Too many positional arguments passed.");
    }

    private void reportTooFewPositionalArguments() {
        throw new IllegalArgumentException("Too few positional arguments passed.");
    }

    private void reportNotFlagInShortFormComposite(String flag, String composite) {
        throw new IllegalArgumentException("Only flags allowed in short-form composites. " +
                "'" + flag + "' is not a flag in '" + composite + "'.");
    }
}
