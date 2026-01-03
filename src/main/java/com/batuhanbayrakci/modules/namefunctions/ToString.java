package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;

/**
 * Yığının en üstündeki nesneyi dizge tipine dönüştürür.
 * (nesne S' -> dizge S')
 */
public class ToString implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject<?> arg = stack.getArgument();

        if (arg instanceof ZyString) {
            stack.push(new ZyString(((String) arg.getValue())));
        }
        stack.push(new ZyString(arg.toString()));
    }
}

