package com.batuhanbayrakci.exception;

public class ZyStackUnderflowError extends ZyError {

    public ZyStackUnderflowError(String message) {
        super(message);
        this.type = "Yığın Yetersiz Eleman Hatası";
    }

    public ZyStackUnderflowError(String message, int line) {
        super(message, line);
        this.type = "Yığın Yetersiz Eleman Hatası";
    }

}
