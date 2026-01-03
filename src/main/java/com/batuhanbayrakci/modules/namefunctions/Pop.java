package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.scanner.Scanner;

/**
 * Yığının en üstündeki elemanı çıkarır (siler).
 * (a S' -> S')
 */
public class Pop implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        if (stack.isEmpty()) {
            throw new ZyStackUnderflowError("Yığında eleman yok", Scanner.getLine());
        }
        stack.pop();
    }
}

