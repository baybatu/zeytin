package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyIndexBoundError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

/**
 * Listeden belirli indisteki elemanı alır.
 * (liste indis S' -> eleman S')
 */
public class ListElement implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject argIndis = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyList)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        if (!(argIndis instanceof ZyNumber)) {
            throw new ZyTypeError("'" + argIndis.getType()
                    + "' tipi, indis için geçersiz argüman tipidir.", SourceMap.getLineOf(argIndis));
        }

        ZyList tumListe = (ZyList) argNesne;
        ZyNumber indis = (ZyNumber) argIndis;

        int i_indis = indis.intValue();

        if (i_indis >= tumListe.size() || i_indis < 0) {
            throw new ZyIndexBoundError("Listeye erişimde indis hatası oluştu. " +
                    "Listedeki eleman sayısı: '" + tumListe.size() + "', verilen indis: " +
                    "'" + indis.intValue() + "'", SourceMap.getLineOf(argIndis));
        }

        ZyObject alinanNesne = tumListe.getValue().get(i_indis);
        stack.push(alinanNesne);
    }
}

