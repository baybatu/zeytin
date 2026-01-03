package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Listeye eleman ekler.
 * (liste eleman S' -> S')
 */
public class ListAdd implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject argEklenecek = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyList)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        ZyList tumListe = (ZyList) argNesne;
        tumListe.getValue().add(argEklenecek);
    }
}

