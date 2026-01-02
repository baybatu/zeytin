package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.List;

public class EqualOperator implements ZyOperatorFunction {

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).equals(arg.get(0))) {
            stack.push(new ZyBoolean(true));
        } else {
            stack.push(new ZyBoolean(false));
        }
    }
}

