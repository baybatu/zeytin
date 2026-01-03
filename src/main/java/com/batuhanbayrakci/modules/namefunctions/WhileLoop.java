package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyProcedure;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * While döngüsü. Koşul doğru olduğu sürece işlemi tekrarlar.
 * Kullanım: işlem {koşul} surece
 * (işlem {koşul} S' -> S')
 */
public class WhileLoop implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(0) instanceof ZyProcedure)) {
            throw new ZyTypeError("'" + arg.get(0).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyProcedure kosul = (ZyProcedure) arg.get(0);
        ZyObject tekrarlanacak = arg.get(1);

        kosul.execute(stack);
        ZyBoolean kontrol = stack.getBooleanArgument();

        while (kontrol.getValue()) {
            tekrarlanacak.execute(stack);
            kosul.execute(stack);
            kontrol = stack.getBooleanArgument();
        }
    }
}

