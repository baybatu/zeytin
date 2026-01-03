package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.exception.ZyValueError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Yığının en üstündeki nesneyi sayı tipine dönüştürür.
 * (dizge/sayı S' -> sayı S')
 */
public class ToNumber implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            try {
                double donusum = Double.parseDouble((String) arg.getValue());
                stack.push(new ZyNumber(donusum));
            } catch (NumberFormatException nfe) {
                throw new ZyValueError("Sayı biçimine uygun olmayan bir dizge kullanıldığından " +
                        "doğru dönüşüm yapılamaz: " + arg, SourceMap.getLineOf(arg));
            }
        } else if (arg instanceof ZyNumber) {
            stack.push(arg);
        } else {
            throw new ZyTypeError("sayı isminin çalışabilmesi için argümanın " +
                    "'sayı' veya 'dizge' tipinde olması gerekir. Mevcut type ise bir" +
                    " '" + arg.getType() + "'", SourceMap.getLineOf(arg));
        }
    }
}

