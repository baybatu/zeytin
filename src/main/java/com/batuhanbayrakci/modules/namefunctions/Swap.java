package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.List;

/**
 * (a b S' -> b a S')
 * */
public class Swap implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        stack.push(arg.get(0));
        stack.push(arg.get(1));
    }
}

