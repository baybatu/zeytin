package com.batuhanbayrakci.exception;

public class ZySyntaxError extends ZyError {

    public ZySyntaxError(String message, int line) {
        super(message, line);
        this.type = "Söz Dizim Hatası";
    }

}