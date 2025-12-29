package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyError;

import java.util.Stack;

public abstract class ZyObject<T> {

    private T value;

    public ZyObject(T value) {
        this.value = value;
    }

    public abstract String getType();

    public void process(ZyStack stack) throws ZyError {
        stack.add(this);
    }

    public void execute(ZyStack stack) throws ZyError {
        process(stack);
    }

    //todo:batu:remove
    @Deprecated
    public void add(ZyObject zn) {
        ((Stack<ZyObject>) this.value).add(zn);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZyObject zyObject = (ZyObject) o;

        return value != null ? value.equals(zyObject.value) : zyObject.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}
