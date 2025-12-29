package com.batuhanbayrakci.objects;

public class ZyType extends ZyObject<String> {

    /**
     * herhangi bir iş yapmaz. Sadece nesnelerin
     * tipini göstermek amacıyla kullanılır.
     * 'type' fonksiyonu kullanır.
     */
    public ZyType(ZyObject object) {
        super(object.getType());
    }

    @Override
    public String getType() {
        return "tip";
    }

    @Override
    public String toString() {
        return "<type: '" + this.getValue() + "'>";
    }
}
