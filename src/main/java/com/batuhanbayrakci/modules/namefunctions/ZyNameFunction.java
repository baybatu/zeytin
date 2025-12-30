package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;

public interface ZyNameFunction {

    void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError;
}
