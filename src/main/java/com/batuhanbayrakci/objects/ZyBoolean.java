package com.batuhanbayrakci.objects;

public class ZyBoolean extends ZyObject<Boolean> {

    /**
     * Bu nesnenin çalıştırılabilirliği yoktur
     * bu durumda bir literal gibi işlem görür
     * çalıştırıldığında kendisini yığına atar.
     */
    public ZyBoolean(Boolean value) {
        super(value);
    }

    @Override
    public String getType() {
        return "dogruluk";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ZyBoolean i) {
            return this.getValue() == i.getValue();
        }
        return false;
    }

    @Override
    public String toString() {
        return getValue() ? "d" : "y";
    }
}