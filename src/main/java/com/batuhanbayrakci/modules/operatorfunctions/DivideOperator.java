package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyDivisionByZeroError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.List;

public class DivideOperator implements ZyOperatorFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError, ZyDivisionByZeroError {
        List<ZyObject> args = stack.getArgument(2);

        ZyObject firstObject = args.get(0);
        ZyObject secondObject = args.get(1);

        if (!firstObject.getClass().isAssignableFrom(secondObject.getClass())) {
            throw new ZyTypeError("\"/\" operatörü '"
                    + secondObject.getType() + "' ile '" + firstObject.getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(firstObject));
        }

        if (firstObject instanceof ZyNumber) {
            if ((Double) firstObject.getValue() == 0) {
                throw new ZyDivisionByZeroError("Payda '0' olamaz", SourceMap.getLineOf(firstObject));
            }
            var result = new ZyNumber((Double) secondObject.getValue() / (Double) firstObject.getValue());
            stack.push(result);
        } else {
            throw new ZyTypeError("'" + firstObject.getType()
                    + "' tipi, \"/\" operatörünü desteklemez", SourceMap.getLineOf(firstObject));
        }
    }
}

