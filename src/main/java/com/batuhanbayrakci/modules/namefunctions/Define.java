package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * Yığından bir isim ve bir değer alarak sembol tablosuna ekler.
 * Kullanım: /isim deger t
 * (/isim değer t S' -> S')
 */
public class Define implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(1) instanceof ZyName)) {
            throw new ZyTypeError("İsim tanımlamak için ilk argümanın 'isim'"
                    + " tipinde olması gerekir. Fakat şu an '"
                    + arg.get(1).getType() + "' tipinde", SourceMap.getLineOf(arg.get(0)));
        }

        ZyObject eklenecekNesne = arg.get(0);
        ZyName eklenecekIsim = (ZyName) arg.get(1);
        ZySymbolStack.INSTANCE.addName(eklenecekIsim, eklenecekNesne);
    }
}

