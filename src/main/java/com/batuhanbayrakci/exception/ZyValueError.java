package com.batuhanbayrakci.exception;

public class ZyValueError extends ZyError {

    public ZyValueError(String message, int line) {
        super(message, line);
        this.type = "Değer Hatası";
    }
}
