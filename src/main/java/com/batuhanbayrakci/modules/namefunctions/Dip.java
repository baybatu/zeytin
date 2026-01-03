package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.List;

/**
 * Stack'in ikinci elemanını geçici olarak saklayıp prosedürü çalıştırır,
 * ardından saklanan değeri geri koyar.
 * (x {P} dip S' -> P x S')
 */
public class Dip implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);
        ZyObject savedObject = arg.get(1);
        arg.get(0).execute(stack);
        stack.push(savedObject);
    }
}

