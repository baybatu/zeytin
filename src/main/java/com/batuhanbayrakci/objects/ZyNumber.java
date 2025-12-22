package com.batuhanbayrakci.objects;

/**
 * Bu nesnenin çalıştırılabilirliği yoktur
 * bu durumda bir literal gibi işlem görür
 * çalıştırıldığında kendisini yığına atar.
 */
public class ZyNumber extends ZyObject<Double> {

    public ZyNumber(double value) {
        super(value, false);
    }

    public int intValue() {
        return getValue().intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ZyNumber) {
            ZyNumber i = (ZyNumber) o;
            return getValue().equals(i.getValue());
        }
        return false;
    }

    @Override
    public String getType() {
        return "sayi";
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}