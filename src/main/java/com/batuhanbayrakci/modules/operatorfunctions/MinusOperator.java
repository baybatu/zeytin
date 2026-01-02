package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

public class MinusOperator implements ZyOperatorFunction {

    /**
     * Çıkarma(-) operatörü için kullanılan metot. Kökeni farklı tipler
     * arasındaki işlemlere izin vermez. Fakat örnek olarak doğruluk tipiyle
     * sayı tipi ile çıkarma işlemine girebilir çünkü doğruluk tipinin kökeni
     * sayı tipidir. Fakat dizge ile sayı tipi aynı kökene sahip olmadığı için
     * bu işleme giremez.
     *
     * @param stack İşlemin yapılacağı veya sonucun aktarılacağı veri yığını
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject} tipinde döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Sayı kökeni dışındaki tüm nesnelerle işlemler için
     *                                                             fırlatılır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Çıkarma operatörünün çalışması için
     *                                                             en az 2 argüman gerekir ki eğer yığında 2'den az eleman varsa bu hata fırlatılır.
     */
    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        ZyObject firstObject = arg.get(0);
        ZyObject secondObject = arg.get(1);

        if (!firstObject.getClass().isAssignableFrom(secondObject.getClass())) {
            throw new ZyTypeError("\"-\" operatörü '"
                    + secondObject.getType() + "' ile '" + firstObject.getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(firstObject));
        }

        if (firstObject instanceof ZyNumber) {
            var result = new ZyNumber((Double) secondObject.getValue() - (Double) firstObject.getValue());
            stack.push(result);
        } else {
            throw new ZyTypeError("'" + firstObject.getType()
                    + "' tipi, \"-\" operatörünü desteklemez", SourceMap.getLineOf(firstObject));
        }
    }
}

