package inspiaaa.assembler.parser;

public class ErrorReporter {
    private final String[] lines;
    private final int numberOfLinesToShowOnError;
    private final int numberOfLinesToShowOnWarning;

    public ErrorReporter(String code, int numberOfLinesToShowOnError, int numberOfLinesToShowOnWarning) {
        this.lines = code.split("\n");
        this.numberOfLinesToShowOnError = numberOfLinesToShowOnError;
        this.numberOfLinesToShowOnWarning = numberOfLinesToShowOnWarning;
    }

    private void ensureIsValidLine(int line) {
        if (line <= 0 || line > lines.length) {
            throw new RuntimeException("Internal error: Line (" + line + ") passed to ErrorReporter is out of bounds.");
        }
    }

    public void reportWarning(String message, int line) {
        ensureIsValidLine(line);

        System.out.println();

        System.out.println("Warning on line " + line + ":");
        printCodeFence(line, numberOfLinesToShowOnWarning);
        System.out.println(message);
    }

    public void reportError(String message, int line) {
        ensureIsValidLine(line);

        System.out.println();

        System.out.println("Error on line " + line + ":");
        printCodeFence(line, numberOfLinesToShowOnError);
        System.out.println(message);

        System.exit(-1);
    }

    private void printCodeFence(int line, int numberOfLinesToShow) {
        if (numberOfLinesToShow <= 0) {
            return;
        }

        int lineIndex = line-1;

        int numberOfDigits = Integer.toString(line).length();

        int startLine = Math.max(0, lineIndex - numberOfLinesToShow + 1);
        for (int i = startLine; i <= lineIndex; i ++) {
            String lineNumber = String.format("%" + numberOfDigits + "s", i + 1);
            String lineContents = lines[i];
            System.out.println(lineNumber + " | " + lineContents);
        }

        System.out.print(" ".repeat(numberOfDigits + 3));
        System.out.println("^".repeat(lines[lineIndex].length()));
    }

    public void reportSyntaxError(String message, int line) {
        reportError("Syntax Error: " + message, line);
    }
}
