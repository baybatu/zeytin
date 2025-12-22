package com.batuhanbayrakci.exception;

public class ZyError extends RuntimeException {

    protected String type = null;
    private String message = null;
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
        String mesaj = "Satır: " + this.line +
                "\n" + this.type + ": " + this.message;
        return mesaj;
    }

}
