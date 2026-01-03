package com.batuhanbayrakci;

import com.batuhanbayrakci.modules.BuiltIn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Yorumlayıcının dahili fonksiyonlarının bulunduğu tablo. String tipi
 * isimler, Method tipi ile ifade edilen fonksiyonlara bağlanır.
 */
public class ZySystemTable {

    public static final ZySystemTable INSTANCE = new ZySystemTable();

    @Deprecated
    private final Map<String, Method> systemFunctions = new HashMap<>();

    private ZySystemTable() {
        loadSystemOperators();
    }

    private void loadSystemOperators() {
        try {
            systemFunctions.put("_",
                    BuiltIn.class.getDeclaredMethod("yiginGoruntule", ZyStack.class));
            systemFunctions.put("kaç",
                    BuiltIn.class.getDeclaredMethod("yiginElemanSayisi", ZyStack.class));
            systemFunctions.put("yaz",
                    BuiltIn.class.getDeclaredMethod("yazdir", ZyStack.class));
            systemFunctions.put("oku",
                    BuiltIn.class.getDeclaredMethod("oku", ZyStack.class));
            systemFunctions.put("sembolT",
                    BuiltIn.class.getDeclaredMethod("sembolGoruntule", ZyStack.class));
            systemFunctions.put("gd",
                    BuiltIn.class.getDeclaredMethod("isimDegistir", ZyStack.class));
            systemFunctions.put("tip",
                    BuiltIn.class.getDeclaredMethod("tip", ZyStack.class));
            systemFunctions.put("d",
                    BuiltIn.class.getDeclaredMethod("dogru", ZyStack.class));
            systemFunctions.put("y",
                    BuiltIn.class.getDeclaredMethod("yanlis", ZyStack.class));
            systemFunctions.put("al",
                    BuiltIn.class.getDeclaredMethod("dizgeElemanCek", ZyStack.class));
            systemFunctions.put("uzunluk",
                    BuiltIn.class.getDeclaredMethod("uzunluk", ZyStack.class));
            systemFunctions.put("li",
                    BuiltIn.class.getDeclaredMethod("listeElemanCek", ZyStack.class));
            systemFunctions.put("ekle",
                    BuiltIn.class.getDeclaredMethod("listeElemanEkle", ZyStack.class));
            systemFunctions.put("değiştir",
                    BuiltIn.class.getDeclaredMethod("listeElemanDegistir", ZyStack.class));
            systemFunctions.put("sayi",
                    BuiltIn.class.getDeclaredMethod("sayi", ZyStack.class));
            systemFunctions.put("dizge",
                    BuiltIn.class.getDeclaredMethod("dizge", ZyStack.class));
            systemFunctions.put("dogruluk",
                    BuiltIn.class.getDeclaredMethod("dogruluk", ZyStack.class));
            systemFunctions.put("isim",
                    BuiltIn.class.getDeclaredMethod("isim", ZyStack.class));
            systemFunctions.put("liste",
                    BuiltIn.class.getDeclaredMethod("liste", ZyStack.class));
            systemFunctions.put("surece",
                    BuiltIn.class.getDeclaredMethod("surece", ZyStack.class));
            systemFunctions.put("yuru",
                    BuiltIn.class.getDeclaredMethod("yuru", ZyStack.class));
            systemFunctions.put("dip",
                    BuiltIn.class.getDeclaredMethod("dip", ZyStack.class));

        } catch (SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Yorumlayıcının dahili fonksiyonlarını barındıran tabloda arama yapan
     * ve {@link java.lang.reflect.Method} tipinde döndüren metot.
     *
     * @param name sistem fonksiyonları tablosunda aranacak dizge
     * @return aranan isim bulunursa bulunan dizgeye karşılık gelen Method tipi
     * nesne döndürülür. Bulunamazsa <code>null</code> döndürlür.
     */
    public Method findName(String name) {
        return systemFunctions.get(name);
    }
}
