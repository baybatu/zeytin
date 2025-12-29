package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.List;

public class ObjectProcessor {

    private final ZyStack stack;

    public ObjectProcessor() {
        this.stack = new ZyStack();
    }

    public void process(List<ZyObject<?>> objects) {
        try {
            for (ZyObject<?> object : objects) {
                object.process(stack);
            }
        } catch (ZyError zh) {
            System.out.println(zh.getMessage());
        }
    }

    public ZyStack getStack() {
        return this.stack;
    }

    public void showStack() {
        System.out.println("Yığın: " + this.stack);
    }

    public int objectCount() {
        return this.stack.size();
    }
}
