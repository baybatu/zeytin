package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyProcedure;

import java.util.ArrayList;
import java.util.List;

/**
 * Listedeki her elemanı verilen prosedürden geçirir ve sonuç listeyi oluşturur.
 * Kullanım: [1 2 3] {2 *} map
 * (list proc map) -> (list)
 */
public class MapFn implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyProcedure proc = stack.getProcedureArgument();
        ZyList list = stack.getListArgument();

        List<ZyObject<?>> resultList = new ArrayList<>();

        for (ZyObject<?> element : list.getValue()) {
            stack.push(element);
            proc.execute(stack);
            ZyObject<?> result = stack.getArgument();
            resultList.add(result);
        }

        stack.push(new ZyList(resultList));
    }
}