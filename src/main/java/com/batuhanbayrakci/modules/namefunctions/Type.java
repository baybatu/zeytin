package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyType;

/**
 * Yığındaki en üstteki elemanın tipini döndürür.
 * (a S' -> tip S')
 */
public class Type implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();
        stack.push(new ZyType(arg));
    }
}

