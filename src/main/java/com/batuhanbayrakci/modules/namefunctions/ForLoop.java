package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.exception.ZyValueError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * For döngüsü. Başlangıçtan sona kadar belirli aralıklarla işlemi tekrarlar.
 * Kullanım: başlangıç son aralık {işlem} yürü
 * (başlangıç son aralık {işlem} S' -> S')
 */
public class ForLoop implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(4);

        if (!(arg.get(1) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(1).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(1)));
        } else if (!(arg.get(2) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(2).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(2)));
        } else if (!(arg.get(3) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(3).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(3)));
        }

        ZyNumber baslangic = (ZyNumber) arg.get(3);
        ZyNumber son = (ZyNumber) arg.get(2);
        ZyNumber aralik = (ZyNumber) arg.get(1);

        if ((baslangic.getValue() - baslangic.intValue() > 0) ||
                (son.getValue() - son.intValue() > 0) ||
                (aralik.getValue() - aralik.intValue() > 0)) {
            throw new ZyValueError("Döngü sayı değerleri tam sayı olmalıdır.", SourceMap.getLineOf(baslangic));
        }

        if (baslangic.getValue() > son.getValue()) {
            throw new ZyValueError("Döngü 'başlangıç' değeri, 'son' değerinden küçük " +
                    "olmalıdır. Şu an başlangıç değeri: '" + baslangic.getValue() + "' ve son değeri: " +
                    "'" + son.getValue() + "' şeklindedir.", SourceMap.getLineOf(baslangic));
        }

        if (aralik.intValue() == 0) {
            throw new ZyValueError("Döngü aralığı 0'dan farklı bir sayı olmalıdır.", SourceMap.getLineOf(aralik));
        }

        ZyObject tekrarlanacak = arg.get(0);

        for (int i = baslangic.intValue(); i < son.intValue(); i += aralik.intValue()) {
            tekrarlanacak.execute(stack);
        }
    }
}

