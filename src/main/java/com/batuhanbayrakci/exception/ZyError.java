package com.batuhanbayrakci.exception;

import java.io.Serial;

public class ZyError extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4372816058610900646L;

    protected String type;
    private final String message;
    private int line = 0;

    public ZyError(String message) {
        super(message);
        this.message = message;
        this.type = "Zeytin Genel Hata";
    }

    public ZyError(String message, int line) {
        super(message);
        this.message = message;
        this.type = "Zeytin Genel Hata";
        this.line = line;
    }

    @Override
    public String getMessage() {
        return "Satır: " + this.line +
                "\n" + this.type + ": " + this.message;
    }

}
