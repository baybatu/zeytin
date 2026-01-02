package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

public class IncrementOperator implements ZyOperatorFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyNumber) {
            var result = new ZyNumber(1 + (Double) arg.getValue());
            stack.push(result);
        } else {
            throw new ZyTypeError("'" + arg.getType()
                    + "' tipi, \"++\" operatörünü desteklemez", SourceMap.getLineOf(arg));
        }
    }
}

