package com.batuhanbayrakci.exception;

public class ZyNameError extends ZyError {

    public ZyNameError(String message, int line) {
        super(message, line);
        this.type = "İsim Hatası";
    }

}
