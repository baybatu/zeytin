package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyIndexBoundError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Dizgeden belirli indisteki elemanı alır.
 * (dizge indis S' -> karakter S')
 */
public class StringElement implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject argIndis = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyString)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        if (!(argIndis instanceof ZyNumber)) {
            throw new ZyTypeError("'" + argIndis.getType()
                    + "' tipi, indis için geçersiz argüman tipidir.", SourceMap.getLineOf(argIndis));
        }

        ZyString tumDizge = (ZyString) argNesne;
        ZyNumber indis = (ZyNumber) argIndis;

        int i_indis = indis.intValue();

        if (i_indis >= tumDizge.length() || i_indis < 0) {
            throw new ZyIndexBoundError("Dizgeye erişimde indis hatası oluştu. " +
                    "Dizge uzunluğu: '" + tumDizge.length() + "', verilen indis: " +
                    "'" + indis.intValue() + "'", SourceMap.getLineOf(argIndis));
        }

        char c = tumDizge.getValue().charAt(i_indis);
        ZyString eleman = new ZyString(Character.toString(c));
        stack.push(eleman);
    }
}

