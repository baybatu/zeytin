package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Yığının en üstündeki nesneyi isim tipine dönüştürür.
 * Dizge → /isim şeklinde literal isim oluşturur.
 * (dizge/isim S' -> isim S')
 */
public class ToName implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            stack.push(ZyName.createLiteral((String) arg.getValue()));
        } else if (arg instanceof ZyName) {
            stack.push(arg);
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'isim' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }
}

