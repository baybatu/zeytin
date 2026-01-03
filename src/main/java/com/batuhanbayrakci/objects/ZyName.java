package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.ZySystemTable;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.exception.ZyNameError;
import com.batuhanbayrakci.modules.BuiltIn;
import com.batuhanbayrakci.modules.namefunctions.Define;
import com.batuhanbayrakci.modules.namefunctions.Dup;
import com.batuhanbayrakci.modules.namefunctions.Execute;
import com.batuhanbayrakci.modules.namefunctions.If;
import com.batuhanbayrakci.modules.namefunctions.Loop;
import com.batuhanbayrakci.modules.namefunctions.Mod;
import com.batuhanbayrakci.modules.namefunctions.Pop;
import com.batuhanbayrakci.modules.namefunctions.Swap;
import com.batuhanbayrakci.modules.namefunctions.ZyNameFunction;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


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

    private final boolean executable;
    private static final Map<String, ZyNameFunction> systemNameFunctions = new HashMap<>();

    static {
        loadSystemNameFunctions();
    }

    private static void loadSystemNameFunctions() {
        systemNameFunctions.put("eger", new If());
        systemNameFunctions.put("tekrarla", new Loop());
        systemNameFunctions.put("mod", new Mod());
        systemNameFunctions.put("yd", new Swap());
        systemNameFunctions.put("calistir", new Execute());
        systemNameFunctions.put("cikar", new Pop());
        systemNameFunctions.put("cift", new Dup());
        systemNameFunctions.put("t", new Define());
    }

    /**
     * ZyIsim türünde bir isim nesnesi oluşturur.
     *
     * @param value      nesneyi temsil edecek "isim"
     * @param executable nesnenin hemen mi yoksa
     *                   gecikmeli mi çalıştırılacağını
     *                   belirler
     */
    private ZyName(String value, boolean executable) {
        super(value);
        this.executable = executable;
    }

    public static ZyName createExecutable(String value) {
        return new ZyName(value, true);
    }

    public static ZyName createLiteral(String value) {
        return new ZyName(value, false);
    }

    public Optional<ZyNameFunction> findNameFunction(String name) {
        return Optional.ofNullable(systemNameFunctions.get(name));
    }

    @Override
    public String getType() {
        return "isim";
    }

    /**
     * {@link com.batuhanbayrakci.ObjectProcessor} tipi bir nesnenin tetiklediği metot.
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
        if (executable) {
            execute(stack);
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
     * @param stack Ana yığın
     * @throws com.batuhanbayrakci.exception.ZyError
     */
    @Override
    public void execute(ZyStack stack) throws ZyError {
        ZyObject<?> objectForExecution = ZySymbolStack.INSTANCE.findName(this);

        if (objectForExecution == null) {
            Method met = ZySystemTable.INSTANCE.findName(getValue());
            if (met == null) {
                var nameFunction = findNameFunction(getValue());
                if (nameFunction.isEmpty()) {
                    throw new ZyNameError("\"" + this.getValue() + "\"" + " ismi bulunamadı.", SourceMap.getLineOf(this));
                }
                nameFunction.get().process(stack);
            } else {
                try {
                    met.invoke(BuiltIn.class, stack);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Throwable th = e.getTargetException();
                    throw (ZyError) th;
                }
            }
        } else {
            objectForExecution.execute(stack);
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
        if (o instanceof ZyName i) {
            return getValue().equals(i.getValue());
        }
        return false;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
