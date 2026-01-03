package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.ArrayList;

/**
 * Yığının en üstündeki nesneyi liste tipine dönüştürür.
 * Dizge → her karakter ayrı eleman olur.
 * Liste → olduğu gibi döner.
 * (dizge/liste S' -> liste S')
 */
public class ToList implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            char[] karakterler = ((String) arg.getValue()).toCharArray();

            var fff = new ArrayList<ZyObject<?>>();
            for (char c : karakterler) {
                fff.add(new ZyString(Character.toString(c)));
            }
            stack.push(new ZyList(fff));
        } else if (arg instanceof ZyList) {
            stack.push(arg);
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'liste' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }
}

