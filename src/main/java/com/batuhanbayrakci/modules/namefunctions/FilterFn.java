package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyProcedure;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Listedeki elemanları verilen predicate prosedürüne göre filtreler.
 * Sadece prosedür doğru (d) döndüren elemanlar sonuç listesine eklenir.
 * Kullanım: [1 2 3 4] {3 <=} filtrele -> [1 2 3]
 * (list proc filtrele) -> (list)
 */
public class FilterFn implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyProcedure proc = stack.getProcedureArgument();
        ZyList list = stack.getListArgument();

        List<ZyObject<?>> resultList = new ArrayList<>();

        for (ZyObject<?> element : list.getValue()) {
            stack.push(element);
            proc.execute(stack);
            ZyObject<?> result = stack.getArgument();

            if (!(result instanceof ZyBoolean condition)) {
                throw new ZyTypeError(
                        "filtrele prosedürü doğruluk değeri döndürmeli. "
                                + "Dönen tip: '" + result.getType() + "'",
                        SourceMap.getLineOf(result));
            }

            if (condition.getValue()) {
                resultList.add(element);
            }
        }

        stack.push(new ZyList(resultList));
    }
}
