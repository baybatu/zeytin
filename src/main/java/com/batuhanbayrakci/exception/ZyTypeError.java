package com.batuhanbayrakci.exception;

public class ZyTypeError extends ZyError {

    public ZyTypeError(String message, int line) {
        super(message, line);
        this.type = "Tip Hatası";
    }

}
