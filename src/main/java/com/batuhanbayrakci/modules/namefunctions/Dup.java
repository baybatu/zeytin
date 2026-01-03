    package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;

/**
 * Yığının en üstündeki elemanı çiftler (kopyalar).
 * (a S' -> a a S')
 */
public class Dup implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();
        stack.push(arg);
        stack.push(arg);
    }
}

