package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

public class MultiplyOperator implements ZyOperatorFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        ZyObject firstObject = arg.get(0);
        ZyObject secondObject = arg.get(1);

        if (!firstObject.getClass().isAssignableFrom(secondObject.getClass())) {
            throw new ZyTypeError("\"*\" operatörü '"
                    + secondObject.getType() + "' ile '" + firstObject.getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(firstObject));
        }

        if (firstObject instanceof ZyNumber) {
            var result = new ZyNumber((Double) secondObject.getValue() * (Double) firstObject.getValue());
            stack.push(result);
        } else {
            throw new ZyTypeError("'" + firstObject.getType()
                    + "' tipi, \"*\" operatörünü desteklemez", SourceMap.getLineOf(firstObject));
        }
    }
}

