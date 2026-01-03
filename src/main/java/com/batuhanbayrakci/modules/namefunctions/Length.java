package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Dizge veya liste uzunluğunu hesaplar.
 * (dizge/liste S' -> uzunluk S')
 */
public class Length implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (!(arg instanceof ZyString) && !(arg instanceof ZyList)) {
            throw new ZyTypeError("'" + arg.getType()
                    + "' tipi, uzunluk hesabı için geçersiz argüman tipidir.", SourceMap.getLineOf(arg));
        }

        if (arg instanceof ZyString tumDizge) {
            stack.push(new ZyNumber(tumDizge.length()));
        } else {
            ZyList liste = (ZyList) arg;
            stack.push(new ZyNumber(liste.size()));
        }
    }
}

