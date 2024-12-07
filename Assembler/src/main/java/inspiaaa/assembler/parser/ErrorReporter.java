package inspiaaa.assembler.parser;

public class ErrorReporter {
    private final String[] lines;
    private final int numberOfLinesToShow;

    public ErrorReporter(String code, int numberOfLinesToShow) {
        this.lines = code.split("\n");
        this.numberOfLinesToShow = numberOfLinesToShow;
    }

    public void reportError(String message, int line) {
        System.out.println();

        System.out.println("Error in line " + line + ":");
        printCodeFence(line);
        System.out.println(message);

        System.exit(-1);
    }

    private void printCodeFence(int line) {
        if (numberOfLinesToShow <= 0) {
            return;
        }

        int lineIndex = line-1;

        int numberOfDigits = Integer.toString(line).length();

        int startLine = Math.max(0, lineIndex - numberOfLinesToShow);
        for (int i = startLine; i <= lineIndex; i ++) {
            String lineNumber = String.format("%" + numberOfDigits + "s", i + 1);
            String lineContents = lines[i];
            System.out.println(lineNumber + " | " + lineContents);
        }

        System.out.print(" ".repeat(numberOfDigits + 3));
        System.out.println("^".repeat(lines[lineIndex].length()));
    }
}
