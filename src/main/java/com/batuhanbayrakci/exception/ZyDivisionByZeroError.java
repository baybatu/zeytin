package com.batuhanbayrakci.exception;

public class ZyDivisionByZeroError extends ZyError {

    public ZyDivisionByZeroError(String message, int line) {
        super(message, line);
        this.type = "Sıfıra Bölme Hatası";
    }

}
