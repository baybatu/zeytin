package com.batuhanbayrakci;

import com.batuhanbayrakci.modules.BuiltIn;
import com.batuhanbayrakci.objects.ZyObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Yorumlayıcının dahili fonksiyonlarının bulunduğu tablo. String tipi
 * isimler, Method tipi ile ifade edilen fonksiyonlara bağlanır.
 */
public class ZySystemTable {

    public static final ZySystemTable INSTANCE = new ZySystemTable();

    private Map<String, Method> systemFunctions = new HashMap<>();

    private ZySystemTable() {
        loadSystemOperators();
    }

    private void loadSystemOperators() {
        try {
            systemFunctions.put("+",
                    BuiltIn.class.getDeclaredMethod("topla", ZyStack.class));
            systemFunctions.put("-",
                    BuiltIn.class.getDeclaredMethod("cikar", ZyStack.class));
            systemFunctions.put("*",
                    BuiltIn.class.getDeclaredMethod("carp", ZyStack.class));
            systemFunctions.put("/",
                    BuiltIn.class.getDeclaredMethod("bol", ZyStack.class));
            systemFunctions.put("mod",
                    BuiltIn.class.getDeclaredMethod("mod", ZyStack.class));
            systemFunctions.put("++",
                    BuiltIn.class.getDeclaredMethod("artir", ZyStack.class));
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
            systemFunctions.put("cikar",
                    BuiltIn.class.getDeclaredMethod("elemanCikar", ZyStack.class));
            systemFunctions.put("cift",
                    BuiltIn.class.getDeclaredMethod("elemanCiftle", ZyStack.class));
            systemFunctions.put("t",
                    BuiltIn.class.getDeclaredMethod("tanimla", ZyStack.class));
            systemFunctions.put("gd",
                    BuiltIn.class.getDeclaredMethod("isimDegistir", ZyStack.class));
            systemFunctions.put("calis",
                    BuiltIn.class.getDeclaredMethod("calis", ZyStack.class));
            systemFunctions.put("tip",
                    BuiltIn.class.getDeclaredMethod("tip", ZyStack.class));
            systemFunctions.put("d",
                    BuiltIn.class.getDeclaredMethod("dogru", ZyStack.class));
            systemFunctions.put("y",
                    BuiltIn.class.getDeclaredMethod("yanlis", ZyStack.class));
            systemFunctions.put("=",
                    BuiltIn.class.getDeclaredMethod("esitlik", ZyStack.class));
            systemFunctions.put("<",
                    BuiltIn.class.getDeclaredMethod("kucukluk", ZyStack.class));
            systemFunctions.put("<=",
                    BuiltIn.class.getDeclaredMethod("kucukEsitlik", ZyStack.class));
            systemFunctions.put(">",
                    BuiltIn.class.getDeclaredMethod("buyukluk", ZyStack.class));
            systemFunctions.put(">=",
                    BuiltIn.class.getDeclaredMethod("buyukEsitlik", ZyStack.class));
            systemFunctions.put("eger",
                    BuiltIn.class.getDeclaredMethod("kosul", ZyStack.class));
            systemFunctions.put("tekrar",
                    BuiltIn.class.getDeclaredMethod("tekrarla", ZyStack.class));
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
            systemFunctions.put("yd",
                    BuiltIn.class.getDeclaredMethod("elemanYerDegistir", ZyStack.class));
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
     * @param    object    sistem fonksiyonları tablosunda aranacak dizge
     * @return aranan isim bulunursa bulunan dizgeye karşılık gelen Method tipi
     * nesne döndürülür. Bulunamazsa <code>null</code> döndürlür.
     */
    public Method findName(ZyObject zyObject) {
        return systemFunctions.get(zyObject.getValue());
    }
}
