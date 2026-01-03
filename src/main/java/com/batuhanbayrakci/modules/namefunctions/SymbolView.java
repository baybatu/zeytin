package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;

/**
 * Sembol tablosunu görüntüler.
 * (S' -> S')
 */
public class SymbolView implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        System.out.println("Sembol Yığını: " + ZySymbolStack.INSTANCE);
    }
}

