package inspiaaa.assembler.parser;

import java.util.Objects;

public class Location {
    private final String file;
    private final int line;
    private final int column;
    private final int length;

    public Location(String file, int line, int column, int length) {
        this.file = file;
        this.line = line;
        this.column = column;
        this.length = Math.max(length, 1);
    }

    public Location(String file, int line, int column) {
        this(file, line, column, 1);
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLength() {
        return length;
    }

    public Location nextColumn() {
        return new Location(file, line, column + length, 1);
    }

    @Override
    public String toString() {
        return file + ":" + line + ":" + column + " (" + length + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return line == location.line && column == location.column && length == location.length && Objects.equals(file, location.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, line, column, length);
    }

    public static Location merge(Location start, Location end) {
        if (!start.file.equals(end.file) || start.line != end.line) {
            throw new IllegalArgumentException(
                    "Start location '" + start + "' is incompatible with end location '" + end + "'.");
        }

        int endEndColumn = end.column + end.length;
        int length = endEndColumn - start.column;

        return new Location(start.file, end.line, start.column, length);
    }
}
