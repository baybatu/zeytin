package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ZyStack extends Stack<ZyObject> {

    public ZyObject getArgument() {
        return getArgument(1).get(0);
    }

    public List<ZyObject> getArgument(int popCount) {
        if (size() < popCount) {
            throw new ZyStackUnderflowError(
                    "Bu işlem için yığında yeterli eleman yok. "
                            + "Gerekli argüman sayısı: " + popCount
                            + ". Mevcut eleman sayısı: " + size());
        }
        List<ZyObject> argList = new ArrayList<>();
        for (int i = 0; i < popCount; i++) {
            argList.add(pop());
        }
        return argList;
    }

}