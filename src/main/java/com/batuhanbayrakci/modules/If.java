package com.batuhanbayrakci.modules;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

/**
 * This is a ZyNameFunction
 * Usage: BOOL OBJ1 OBJ2 eger
 * It execute OBJ1 if  
 */
public class If {

    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(3);

        if (!(arg.get(2) instanceof ZyBoolean)) {
            throw new ZyTypeError("'" + arg.get(2).getType()
                    + "' tipi, koşul için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyBoolean kosul = (ZyBoolean) arg.get(2);
        ZyObject yapilacakIlk = arg.get(0);
        ZyObject yapilacakIkinci = arg.get(1);

        if (kosul.getValue()) {
            yapilacakIkinci.execute(stack);
        } else {
            yapilacakIlk.execute(stack);
        }
    }
}
