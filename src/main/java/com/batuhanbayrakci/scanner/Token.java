package com.batuhanbayrakci.scanner;

public class Token {

    private int type;
    private int start;
    private int end;
    private int line;

    public Token(int type, int start, int end, int line) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.line = line;
    }

    public int getType() {
        return this.type;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getLine() {
        return this.line;
    }

    @Override
    public String toString() {
        return String.format("T[%s, %2d, %2d, %2d]", type, start, end, line);
    }

}