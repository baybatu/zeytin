package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;

/**
 * Yığının en üstündeki elemanı yazdırır.
 * (S' yaz -> S')
 */
public class Print implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject yazilacakNesne = stack.lastElement();
        if (yazilacakNesne instanceof ZyString) {
            System.out.println(((ZyString) yazilacakNesne).print());
        }
        System.out.println(yazilacakNesne.toString());
    }
}

