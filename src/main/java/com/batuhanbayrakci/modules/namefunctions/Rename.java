package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.exception.ZyNameError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * Sembol tablosundaki bir ismin değerini değiştirir.
 * (/isim yeniDeger gd S' -> S')
 */
public class Rename implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(1) instanceof ZyName)) {
            throw new ZyTypeError("İsim tanımlamak için ilk argümanın 'isim'"
                    + " tipinde olması gerekir. Fakat şu anki durumda '"
                    + arg.get(1).getType() + "' tipinde", SourceMap.getLineOf(arg.get(0)));
        }

        ZyObject degisecekNesne = arg.get(0);
        ZyName degisecekIsim = (ZyName) arg.get(1);
        boolean test = ZySymbolStack.INSTANCE.changeName(degisecekIsim, degisecekNesne);
        if (!test) {
            throw new ZyNameError("\"" + degisecekIsim + "\""
                    + " ismi üst kapsamlarda bulunamadı.", SourceMap.getLineOf(arg.get(0)));
        }
    }
}

