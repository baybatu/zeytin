package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.ZySystemTable;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.exception.ZyNameError;
import com.batuhanbayrakci.modules.BuiltIn;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * <p>Isim nesneleri fonksiyon ve değişkenlerin ifade edilmesinde
 * kullanılırlar.</p>
 *
 * <p>Isim nesneleri ikiye ayrılır: Biri "literal",
 * bir diğeri de "çalıştırılabilir" isim nesneleridir.</p>
 *
 * <p>Literal isim nesnelerinin <code>executable</code>
 * öznitelikleri <code>false</code> şeklindedir ve doğrudan yığına atılırlar.
 * <p>
 * Literal isimler, prosedür ve değişkenlerin tanımlanmaları safhasında kullanılırlar.</p>
 *
 * <p>Çalıştırılabilir isimler ise yorumlayıcı tarafından karşılaşıldıklarında
 * doğrudan çalıştırılırlar. Çalıştırılacak nesneler için önce sembol tablosuna,
 * yoksa sistem tablosuna bakılır.</p>
 */
public class ZyName extends ZyObject<String> {

    /**
     * ZyIsim türünde bir isim nesnesi oluşturur.
     *
     * @param    value                nesneyi temsil edecek "isim"
     * @param    executable    nesnenin hemen mi yoksa
     * gecikmeli mi çalıştırılacağını
     * belirler
     */
    public ZyName(String value, boolean executable) {
        super(value, executable);
    }

    @Override
    public String getType() {
        return "isim";
    }

    /**
     * {@link ZyCalistici} tipi bir nesnenin tetiklediği metot.
     * Oluşabilecek herhangi bir hatayı ZyHata üzerinden çalıştırıcıya
     * gönderir.
     * <p>
     * Eğer <code>executable</code> özniteliği <code>true</code>
     * ise sembol ya da sistem tablolarındaki karşılıkları derhal çalıştırılır.
     * <false> ise isim nesnesi yığına gönderilir.
     *
     * @param stack Ana yığın
     * @throws com.batuhanbayrakci.exception.ZyError
     */
    @Override
    public void process(ZyStack stack) throws ZyError {
        if (isExecutable()) {
            try {
                evaluate(stack);
            } catch (ZyNameError zih) {
                System.out.println(zih.getMessage());
            }
        } else {
            stack.push(this);
        }
    }

    /**
     * <p><code>executable</code> özniteliğinin <code>true</code> olması
     * durumunda dallanılan metot. Bu metotta öncelikle çalıştırılacak isim,
     * kullanıcının tanımladığı isimlerin bulunduğu sembol tablosunda aranır. Eğer bulunursa,
     * o girdideki "değer" kısmını oluşturan {@link ZyObject} tipindeki nesne çalıştırılır.
     * Bulunamazsa yorumlayıcının dahili fonksiyonlarının bulunduğu sistem tablosunda arama yapılır.
     * Bulunursa o ismin karşısında bulunan {@link java.lang.reflect.Method} tipinin ifade ettiği metot tetiklenir.
     * Tetiklenen metot da ana yığına eklenecek nesneyi döndürür.</p>
     *
     * @throws com.batuhanbayrakci.exception.ZyError
     * @param    stack    Ana yığın
     */
    public void evaluate(ZyStack stack) throws ZyError {
        ZyObject objectForExecution = null;

        // once sembol tabloya bak
        objectForExecution = ZySymbolStack.INSTANCE.findName(this);

        // yoksa sistem tablosuna bak
        if (objectForExecution == null) {
            Method met = ZySystemTable.INSTANCE.findName(this);
            // sistem tablosunda da yoksa
            if (met == null) {
                throw new ZyNameError("\"" + (String) this.getValue() + "\"" +
                        " ismi bulunamadı.", SourceMap.getLineOf(this));
            } else {
                try {
                    // sistem tablosunda karşılık gelen metodu tetikle
                    objectForExecution = (ZyObject) met.invoke(BuiltIn.class, stack);
                    // sistem tablosundan çekilen nesneyi çalıştır
                    objectForExecution.process(stack);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // getTargetException ile hangi type hatayı
                    // yakaladığını buluruz
                    Throwable th = e.getTargetException();
                    throw (ZyError) th;
                }
            }
        } else {
            // kullanıcı tanımlı isim çalıştırmadan önce...
            objectForExecution.setExecutable(true);
            objectForExecution.process(stack);
            objectForExecution.setExecutable(false);
        }

    }

    /**
     * Sembol yığınında bulunan tablolarda isim araması yapmak için
     * tekrar tanımlanan metot. String'lerin sahip olduğu hash kodu üreticisi
     * kullanılmıştır.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < getValue().length(); i++) {
            hash = hash * 31 + getValue().charAt(i);
        }
        return hash;
    }

    /**
     * Sembol yığınında bulunan tablolarda isim araması yapmak için
     * tekrar tanımlanan metot.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ZyName) {
            ZyName i = (ZyName) o;
            return getValue().equals(i.getValue());
        }
        return false;
    }

    /**
     * Nesnenin karakter biçiminde gösterimini sunar
     */
    @Override
    public String toString() {
        return getValue();
    }

}
