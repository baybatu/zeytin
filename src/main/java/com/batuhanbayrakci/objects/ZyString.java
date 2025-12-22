package com.batuhanbayrakci.objects;

public class ZyString extends ZyObject<String> {

    /**
     * Bu nesnenin çalıştırılabilirliği yoktur
     * bu durumda bir literal gibi işlem görür
     * çalıştırıldığında kendisini yığına atar.
     */
    public ZyString() {
    }

    public ZyString(String value) {
        super(value, false);
    }

    @Override
    public String getType() {
        return "dizge";
    }

    /**
     * Dizgenin çıktıya verildiğinde çağrılacak metot.
     * Bu çıktı standart çıktı olabileceği gibi dosya veya
     * başka bir aygıt da olabilir.
     * <p>
     * Bu metotta dizgenin içerisindeki özel karakterler yorumlanır
     * ve çıktıya tırnak işaretleri olmadan verilir.
     * <p>
     * \n, \t gibi temel kaçış karakteriyle beraber, " işaretleri de
     * ayıklanır.
     */
    public String print() {
        String strValue = getValue();
        strValue = strValue.replace("\\n", "\n");
        strValue = strValue.replace("\\t", "\t");
        strValue = strValue.replace("\\\\", "\\");
        strValue = strValue.replace("\\\"", "\"");
        return strValue;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ZyString) {
            ZyString otherStr = (ZyString) o;
            return getValue().equals(otherStr.getValue());
        }
        return false;
    }

    @Override
    public String toString() {
        return "\"" + getValue() + "\"";
    }

    public int length() {
        return getValue().length();
    }

}
