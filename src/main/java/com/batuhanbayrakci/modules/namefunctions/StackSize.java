package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;

/**
 * Yığındaki eleman sayısını döndürür.
 * (S' -> eleman_sayısı S')
 */
public class StackSize implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        stack.push(new ZyNumber(stack.size()));
    }
}

