package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;

public interface ZyOperatorFunction {

    void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError;
}
