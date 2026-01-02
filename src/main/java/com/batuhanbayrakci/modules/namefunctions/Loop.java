package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * This is a ZyNameFunction for loop iteration
 * Usage: { "islem" } 6 tekrarla
 * It executes the given block N times
 */
public class Loop implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(0) instanceof ZyNumber repeatCount)) {
            throw new ZyTypeError("'" + arg.get(0).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyObject tekrarlanacak = arg.get(1);

        if (repeatCount.getValue() == 0) {
            return;
        }

        if (repeatCount.getValue() < 0) {
            throw new ZyError("Döngülerde tekrar sayısı pozitif olmalıdır.");
        }

        for (int i = 0; i < repeatCount.getValue(); i++) {
            tekrarlanacak.execute(stack);
        }
    }
}

