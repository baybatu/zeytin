package com.batuhanbayrakci.exception;

public class ZyIndexBoundError extends ZyError {

    public ZyIndexBoundError(String message, int line) {
        super(message, line);
        this.type = "İndis Sınır Hatası";
    }
}
