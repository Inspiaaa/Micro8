package inspiaaa.assembler;

import inspiaaa.assembler.parser.Location;

import java.util.HashMap;

public class ErrorReporter {
    private final HashMap<String, String[]> linesByFile;

    private final int numberOfLinesToShowOnError;
    private final int numberOfLinesToShowOnWarning;

    public ErrorReporter(int numberOfLinesToShowOnError, int numberOfLinesToShowOnWarning) {
        this.numberOfLinesToShowOnError = numberOfLinesToShowOnError;
        this.numberOfLinesToShowOnWarning = numberOfLinesToShowOnWarning;

        this.linesByFile = new HashMap<>();
    }

    public void loadFile(String file, String code) {
        linesByFile.put(file, code.split("\n"));
    }

    private void reportInternalError(String message) {
        throw new RuntimeException("Internal error: " + message);
    }

    private void ensureIsValidLocation(Location location) {
        if (!linesByFile.containsKey(location.getFile())) {
            reportInternalError("Location " + location + " passed to ErrorReporter does not exist.");
        }

        String[] lines = linesByFile.get(location.getFile());
        int line = location.getLine();

        if (line <= 0 || line > lines.length) {
            reportInternalError("Location " + location + " passed to ErrorReporter is out of bounds.");
        }
    }

    public void reportWarning(String message, Location location) {
        ensureIsValidLocation(location);

        System.out.println();

        System.out.println("Warning --> " + location);
        printCodeFence(location, numberOfLinesToShowOnWarning);
        System.out.println(message);
    }

    public void reportError(String message, Location location) {
        reportError("Error", message, location);
    }

    public void reportError(String errorType, String message, Location location) {
        ensureIsValidLocation(location);

        System.out.println();

        System.out.println(errorType + " --> " + location);
        printCodeFence(location, numberOfLinesToShowOnError);
        System.out.println(message);

        System.exit(-1);
    }

    private void printCodeFence(Location location, int numberOfLinesToShow) {
        if (numberOfLinesToShow <= 0) {
            return;
        }

        String[] code = linesByFile.get(location.getFile());

        int line = location.getLine();
        int lineIndex = line-1;

        int numberOfDigits = Integer.toString(line).length();

        int startLine = Math.max(0, lineIndex - numberOfLinesToShow + 1);
        for (int i = startLine; i <= lineIndex; i ++) {
            String lineNumber = String.format("%" + numberOfDigits + "s", i + 1);
            String lineContents = code[i];
            System.out.println(lineNumber + " | " + lineContents);
        }

        System.out.print(" ".repeat(numberOfDigits + 3 + location.getColumn()-1));
        System.out.println("^".repeat(location.getLength()));
    }

    public void reportSyntaxError(String message, Location location) {
        reportError("Syntax Error", message, location);
    }
}
