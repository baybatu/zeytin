package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * This is a ZyNameFunction for traditional IF condition
 * Usage: BOOL OBJ1 OBJ2 eger
 * It executes OBJ1 if BOOL is true (d), otherwise executes OBJ2
 */
public class If implements ZyNameFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(3);

        if (!(arg.get(2) instanceof ZyBoolean condition)) {
            throw new ZyTypeError("'" + arg.get(2).getType()
                    + "' tipi, koşul için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        if (condition.getValue()) {
            arg.get(1).execute(stack);
        } else {
            arg.getFirst().execute(stack);
        }
    }
}
