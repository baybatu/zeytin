package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Yığının en üstündeki nesneyi doğruluk tipine dönüştürür.
 * - Dizge: uzunluk > 0 ise d, değilse y
 * - Sayı: 0'dan farklı ise d, değilse y
 * - Liste: eleman sayısı > 0 ise d, değilse y
 * - Doğruluk: olduğu gibi döner
 * (nesne S' -> doğruluk S')
 */
public class ToBoolean implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            if (((ZyString) arg).length() > 0) {
                stack.push(new ZyBoolean(true));
            } else {
                stack.push(new ZyBoolean(false));
            }
        } else if (arg instanceof ZyNumber) {
            if (((ZyNumber) arg).getValue() == 0) {
                stack.push(new ZyBoolean(false));
            } else {
                stack.push(new ZyBoolean(true));
            }
        } else if (arg instanceof ZyList) {
            if (((ZyList) arg).size() == 0) {
                stack.push(new ZyBoolean(false));
            } else {
                stack.push(new ZyBoolean(true));
            }
        } else if (arg instanceof ZyBoolean) {
            stack.push(arg);
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'dogruluk' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }
}

