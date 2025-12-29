package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySystemTable;
import com.batuhanbayrakci.exception.ZyError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ZyOperator extends ZyObject<String> {

    public ZyOperator(String value) {
        super(value);
    }

    @Override
    public String getType() {
        return "operator";
    }

    @Override
    public void process(ZyStack stack) throws ZyError {
        execute(stack);
    }

    @Override
    public void execute(ZyStack stack) throws ZyError {
        try {
            Method methodForExecution = ZySystemTable.INSTANCE.findName(getValue());
            ZyObject objectForAdd = (ZyObject) methodForExecution.invoke(null, stack);
            stack.push(objectForAdd);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Throwable th = e.getTargetException();
            throw (ZyError) th;
        }
    }
}
