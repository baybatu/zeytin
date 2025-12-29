package com.batuhanbayrakci.modules;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.exception.ZyDivisionByZeroError;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.exception.ZyIndexBoundError;
import com.batuhanbayrakci.exception.ZyNameError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.exception.ZyValueError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyEmpty;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyProcedure;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.objects.ZyType;
import com.batuhanbayrakci.scanner.Scanner;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BuiltIn {

    /**
     * Toplama(+) operatörü için kullanılan metot. Kökeni farklı tipler
     * arasındaki işlemlere izin vermez. Fakat örnek olarak doğruluk tipiyle
     * sayı tipi ile toplama işlemine girebilir çünkü doğruluk tipinin kökeni
     * sayı tipidir. Fakat dizge ile sayı tipi aynı kökene sahip olmadığı için
     * bu işleme giremez.
     * <p>
     * Bununla beraber dizgelerin birbirleriyle birleştirilmesi, listelerin de kendi
     * aralarında birleştirilmesi işlemlerinde de kullanılır.
     *
     * @param stack İşlemin yapılacağı veya sonucun aktarılacağı veri yığını
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject} tipinde döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Eğer kökeni farklı iki nesne toplanmaya veya farklı tipte nesneler
     *                                                             toplanmaya çalışılırsa fırlatılır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Toplama operatörünün çalışması için
     *                                                             en az 2 argüman gerekir ki eğer yığında 2'den az eleman varsa bu hata fırlatılır.
     */
    public static ZyObject topla(ZyStack stack) throws ZyTypeError,
            ZyStackUnderflowError {
        List<ZyObject> args = stack.getArgument(2);

        ZyObject firstObject = args.get(0);
        ZyObject secondObject = args.get(1);
        if (firstObject instanceof ZyNumber && secondObject instanceof ZyNumber) {
            return new ZyNumber((Double) secondObject.getValue()
                    + (Double) firstObject.getValue());
        } else if (firstObject instanceof ZyString && secondObject instanceof ZyString) {
            return new ZyString(secondObject.getValue()
                    + (String) firstObject.getValue());
        } else if (firstObject instanceof ZyList && secondObject instanceof ZyList) {
            ((ZyList) secondObject).addAll((ZyList) firstObject);
            return secondObject;

        } else if (!firstObject.getClass().isAssignableFrom(secondObject.getClass())) {
            throw new ZyTypeError("\"+\" operatörü '"
                    + secondObject.getType() + "' ile '" + firstObject.getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(firstObject));
        } else {
            throw new ZyTypeError("'" + firstObject.getType()
                    + "' tipi, \"+\" operatörünü desteklemez", SourceMap.getLineOf(firstObject));
        }

    }

    /**
     * Çıkarma(-) operatörü için kullanılan metot. Kökeni farklı tipler
     * arasındaki işlemlere izin vermez. Fakat örnek olarak doğruluk tipiyle
     * sayı tipi ile çıkarma işlemine girebilir çünkü doğruluk tipinin kökeni
     * sayı tipidir. Fakat dizge ile sayı tipi aynı kökene sahip olmadığı için
     * bu işleme giremez.
     *
     * @param stack İşlemin yapılacağı veya sonucun aktarılacağı veri yığını
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject} tipinde döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Sayı kökeni dışındaki tüm nesnelerle işlemler için
     *                                                             fırlatılır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Çıkarma operatörünün çalışması için
     *                                                             en az 2 argüman gerekir ki eğer yığında 2'den az eleman varsa bu hata fırlatılır.
     */
    public static ZyObject cikar(ZyStack stack) throws ZyTypeError,
            ZyStackUnderflowError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!arg.get(0).getClass().isAssignableFrom(arg.get(1).getClass())) {
            throw new ZyTypeError("\"-\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        } else {
            if (arg.get(0) instanceof ZyNumber) {
                return new ZyNumber((Double) arg.get(1).getValue()
                        - (Double) arg.get(0).getValue());
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \"-\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        }
    }

    public static ZyObject carp(ZyStack stack) throws ZyTypeError,
            ZyStackUnderflowError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!arg.get(0).getClass().isAssignableFrom(arg.get(1).getClass())) {
            throw new ZyTypeError("\"*\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        } else {
            if (arg.get(0) instanceof ZyNumber) {
                return new ZyNumber((Double) arg.get(1).getValue()
                        * (Double) arg.get(0).getValue());
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \"*\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        }
    }

    public static ZyObject bol(ZyStack stack) throws ZyDivisionByZeroError,
            ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> args = stack.getArgument(2);

        if (args.get(0).getClass().isAssignableFrom(args.get(1).getClass())) {
            throw new ZyTypeError("\"/\" operatörü '"
                    + args.get(1).getType() + "' ile '" + args.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(args.get(0)));
        } else {
            if (args.get(0).getClass().equals(ZyNumber.class)) {
                if ((Double) args.get(0).getValue() == 0) {
                    throw new ZyDivisionByZeroError("Payda '0' olamaz", SourceMap.getLineOf(args.get(0)));
                }
                return new ZyNumber((Double) args.get(1).getValue()
                        / (Double) args.get(0).getValue());
            } else {
                throw new ZyTypeError("'" + args.get(0).getType()
                        + "' tipi, \"/\" operatörünü desteklemez", SourceMap.getLineOf(args.get(0)));
            }
        }
    }

    public static ZyObject mod(ZyStack stack) throws ZyDivisionByZeroError,
            ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!arg.get(0).getClass().isAssignableFrom(arg.get(1).getClass())) {
            throw new ZyTypeError("\"mod\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        } else {
            if (arg.get(0) instanceof ZyNumber) {
                return new ZyNumber((Double) arg.get(1).getValue()
                        % (Double) arg.get(0).getValue());
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \"mod\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        }
    }

    public static ZyObject artir(ZyStack stack) throws ZyTypeError,
            ZyStackUnderflowError {
        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyNumber) {
            return new ZyNumber(1 + (Double) arg.getValue());
        } else {
            throw new ZyTypeError("'" + arg.getType()
                    + "' tipi, \"++\" operatörünü desteklemez", SourceMap.getLineOf(arg));
        }
    }

    public static ZyObject yiginGoruntule(ZyStack stack) {
        System.out.println("Yığın: " + stack);
        return new ZyEmpty();
    }

    public static ZyObject yiginElemanSayisi(ZyStack stack) {
        return new ZyNumber(stack.size());
    }

    public static ZyObject sembolGoruntule(ZyStack stack) {
        System.out.println("Sembol stacki: " + ZySymbolStack.INSTANCE);
        return new ZyEmpty();
    }

    public static ZyObject yazdir(ZyStack stack) {
        ZyObject yazilacakNesne = stack.lastElement();
        if (yazilacakNesne instanceof ZyString) {
            System.out.println(((ZyString) yazilacakNesne).print());
        }
        System.out.println(yazilacakNesne.toString());
        return new ZyEmpty();
    }

    public static ZyObject oku(ZyStack stack) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String okunan = null;
        try {
            okunan = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZyString okunanNesne = new ZyString(okunan);
        stack.add(okunanNesne);
        return new ZyEmpty();
    }

    public static ZyObject elemanCikar(ZyStack stack)
            throws ZyStackUnderflowError {
        if (stack.size() == 0) {
            throw new ZyStackUnderflowError("Yığında eleman yok", Scanner.getLine());
        }
        stack.pop();
        return new ZyEmpty();
    }

    public static ZyObject elemanCiftle(ZyStack stack)
            throws ZyStackUnderflowError {
        ZyObject arg = stack.getArgument();
        stack.add(arg);

        return new ZyEmpty();
    }

    public static ZyObject tanimla(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(1) instanceof ZyName)) {
            throw new ZyTypeError("İsim tanımlamak için ilk argümanın 'isim'"
                    + " tipinde olması gerekir. Fakat şu anki durumda '"
                    + arg.get(1).getType() + "' tipinde", SourceMap.getLineOf(arg.get(0)));
        }

        ZyObject eklenecekNesne = arg.get(0);
        ZyName eklenecekIsim = (ZyName) arg.get(1);
        ZySymbolStack.INSTANCE.addName(eklenecekIsim, eklenecekNesne);
        return new ZyEmpty();
    }

    public static ZyObject isimDegistir(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyNameError {
        boolean test = false;
        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(1) instanceof ZyName)) {
            throw new ZyTypeError("İsim tanımlamak için ilk argümanın 'isim'"
                    + " tipinde olması gerekir. Fakat şu anki durumda '"
                    + arg.get(1).getType() + "' tipinde", SourceMap.getLineOf(arg.get(0)));
        }

        ZyObject degisecekNesne = arg.get(0);
        ZyName degisecekIsim = (ZyName) arg.get(1);
        test = ZySymbolStack.INSTANCE.changeName(degisecekIsim, degisecekNesne);
        if (!test) {
            throw new ZyNameError("\"" + degisecekIsim + "\""
                    + " ismi üst kapsamlarda bulunamadı.", SourceMap.getLineOf(arg.get(0)));
        }
        return new ZyEmpty();
    }

    public static ZyObject calis(ZyStack stack) throws ZyError {
        ZyObject arg = stack.getArgument();

        arg.execute(stack);
        return new ZyEmpty();
    }

    public static ZyObject tip(ZyStack stack) throws ZyStackUnderflowError {
        ZyObject arg = stack.getArgument();
        return new ZyType(arg);
    }

    public static void dogru(ZyStack stack) {
        stack.add(new ZyBoolean(true));
    }

    public static void yanlis(ZyStack stack) {
        stack.add(new ZyBoolean(false));
    }

    public static ZyObject esitlik(ZyStack stack)
            throws ZyStackUnderflowError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).equals(arg.get(0))) {
            return new ZyBoolean(true);
        }
        return new ZyBoolean(false);
    }

    public static ZyObject kucukluk(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).getClass().isAssignableFrom(arg.get(0).getClass())) {
            if (arg.get(1) instanceof ZyNumber) {
                if ((Double) arg.get(1).getValue() < (Double) arg.get(0).getValue()) {
                    return new ZyBoolean(true);
                } else {
                    return new ZyBoolean(false);
                }
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \"<\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        } else {
            throw new ZyTypeError("\"<\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        }
    }

    public static ZyObject kucukEsitlik(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).getClass().isAssignableFrom(arg.get(0).getClass())) {
            if (arg.get(1) instanceof ZyNumber) {
                if ((Double) arg.get(1).getValue() <= (Double) arg.get(0).getValue()) {
                    return new ZyBoolean(true);
                } else {
                    return new ZyBoolean(false);
                }
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \"<=\" operatörünü desteklemez",
                        SourceMap.getLineOf(arg.get(0)));
            }
        } else {
            throw new ZyTypeError("\"<=\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        }
    }

    public static ZyObject buyukluk(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).getClass().isAssignableFrom(arg.get(0).getClass())) {
            if (arg.get(1) instanceof ZyNumber) {
                if ((Double) arg.get(1).getValue() > (Double) arg.get(0).getValue()) {
                    return new ZyBoolean(true);
                } else {
                    return new ZyBoolean(false);
                }
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \">\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        } else {
            throw new ZyTypeError("\">\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        }
    }

    public static ZyObject buyukEsitlik(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        List<ZyObject> arg = stack.getArgument(2);

        if (arg.get(1).getClass().isAssignableFrom(arg.get(0).getClass())) {
            if (arg.get(1) instanceof ZyNumber) {
                if ((Double) arg.get(1).getValue() >= (Double) arg.get(0).getValue()) {
                    return new ZyBoolean(true);
                } else {
                    return new ZyBoolean(false);
                }
            } else {
                throw new ZyTypeError("'" + arg.get(0).getType()
                        + "' tipi, \">=\" operatörünü desteklemez", SourceMap.getLineOf(arg.get(0)));
            }
        } else {
            throw new ZyTypeError("\">=\" operatörü '"
                    + arg.get(1).getType() + "' ile '" + arg.get(0).getType()
                    + "' tipleri arası işlemleri desteklemez", SourceMap.getLineOf(arg.get(0)));
        }
    }

    public static void kosul(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        /*
         * if structure
         * example 6 5 = { "esit" } { "esit degil" } eger
         * <BOOL> <OBJ> <OBJ> eger
         *
         */

        List<ZyObject> arg = stack.getArgument(3);

        if (!(arg.get(2) instanceof ZyBoolean)) {
            throw new ZyTypeError("'" + arg.get(2).getType()
                    + "' tipi, koşul için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyBoolean kosul = (ZyBoolean) arg.get(2);
        ZyObject yapilacakIlk = arg.get(0);
        ZyObject yapilacakIkinci = arg.get(1);

        if (kosul.getValue()) {
            yapilacakIkinci.execute(stack);
        } else {
            yapilacakIlk.execute(stack);
        }
    }

    public static void tekrarla(ZyStack stack) throws ZyError {
        /*
         * loop
         *
         * { "islem" } 6 tekrar
         *
         */

        List<ZyObject> arg = stack.getArgument(2);

        if (!(arg.get(0) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(0).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyNumber tekrarSayisi = (ZyNumber) arg.get(0);
        ZyObject tekrarlanacak = arg.get(1);

        if (tekrarSayisi.getValue() > 0) {
            for (int i = 0; i < tekrarSayisi.getValue(); i++) {
                tekrarlanacak.execute(stack);
            }
        } else {
            throw new ZyError("Döngülerde tekrar sayısı pozitif olmalıdır.");
        }
    }

    public static ZyObject dizgeElemanCek(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyIndexBoundError {
        ZyObject argIndis = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyString)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        if (!(argIndis instanceof ZyNumber)) {
            throw new ZyTypeError("'" + argIndis.getType()
                    + "' tipi, indis için geçersiz argüman tipidir.", SourceMap.getLineOf(argIndis));
        }

        ZyString tumDizge = (ZyString) argNesne;
        ZyNumber indis = (ZyNumber) argIndis;

        int i_indis = indis.intValue();

        if (i_indis >= tumDizge.length() || i_indis < 0) {
            throw new ZyIndexBoundError("Dizgeye erişimde indis hatası oluştu. " +
                    "Dizge uzunluğu: '" + tumDizge.length() + "', verilen indis: " +
                    "'" + indis.intValue() + "'", SourceMap.getLineOf(argIndis));
        }

        char c = ((String) tumDizge.getValue()).charAt(i_indis);
        ZyString eleman = new ZyString(Character.toString(c));
        return eleman;
    }

    public static void uzunluk(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyIndexBoundError {
        ZyObject arg = stack.getArgument();

        if (!(arg instanceof ZyString) && !(arg instanceof ZyList)) {
            throw new ZyTypeError("'" + arg.getType()
                    + "' tipi, uzunluk hesabı için geçersiz argüman tipidir.", SourceMap.getLineOf(arg));
        }

        if (arg instanceof ZyString) {
            ZyString tumDizge = (ZyString) arg;
            stack.add(new ZyNumber(tumDizge.length()));
        } else {
            ZyList liste = (ZyList) arg;
            stack.add(new ZyNumber(liste.size()));
        }
    }

    public static ZyObject listeElemanCek(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyIndexBoundError {
        ZyObject argIndis = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyList)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        if (!(argIndis instanceof ZyNumber)) {
            throw new ZyTypeError("'" + argIndis.getType()
                    + "' tipi, indis için geçersiz argüman tipidir.", SourceMap.getLineOf(argIndis));
        }

        ZyList tumListe = (ZyList) argNesne;
        ZyNumber indis = (ZyNumber) argIndis;

        int i_indis = indis.intValue();

        if (i_indis >= tumListe.size() || i_indis < 0) {
            throw new ZyIndexBoundError("Listeye erişimde indis hatası oluştu. " +
                    "Listedeki eleman sayısı: '" + tumListe.size() + "', verilen indis: " +
                    "'" + indis.intValue() + "'", SourceMap.getLineOf(argIndis));
        }

        ZyObject alinanNesne = tumListe.getValue().get(i_indis);
        return alinanNesne;

    }

    public static ZyObject listeElemanEkle(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyIndexBoundError {
        ZyObject argEklenecek = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyList)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        ZyList tumListe = (ZyList) argNesne;
        tumListe.getValue().add(argEklenecek);

        return new ZyEmpty();

    }

    public static ZyObject listeElemanDegistir(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyIndexBoundError {
        ZyObject argYeniNesne = stack.getArgument();
        ZyObject argIndis = stack.getArgument();
        ZyObject argNesne = stack.getArgument();

        if (!(argNesne instanceof ZyList)) {
            throw new ZyTypeError("'" + argNesne.getType()
                    + "' tipi, eleman erişimi için geçersiz argüman tipidir.", SourceMap.getLineOf(argNesne));
        }

        if (!(argIndis instanceof ZyNumber)) {
            throw new ZyTypeError("'" + argIndis.getType()
                    + "' tipi, indis için geçersiz argüman tipidir.", SourceMap.getLineOf(argIndis));
        }

        ZyList tumListe = (ZyList) argNesne;
        ZyNumber indis = (ZyNumber) argIndis;

        int i_indis = indis.intValue();

        if (i_indis >= tumListe.size() || i_indis < 0) {
            throw new ZyIndexBoundError("Listeye erişimde indis hatası oluştu. " +
                    "Listedeki eleman sayısı: '" + tumListe.size() + "', verilen indis: " +
                    "'" + indis.intValue() + "'", SourceMap.getLineOf(argIndis));
        }

        tumListe.getValue().remove(i_indis);
        tumListe.getValue().add(i_indis, argYeniNesne);

        return new ZyEmpty();

    }

    /**
     * Yığının en üstündeki nesneyi sayı tipine dönüştürüp
     * yığına veren metot.
     * <p>
     * Eğer çekilen argüman sayı ise olduğu gibi yeniden yığına
     * verilir. Yani kopyalanmış gibi olur.
     * <p>
     * Fakat eğer çekilen argüman dizge tipindeyse, onun sayı biçime
     * uygun olup olmadığı kontrol edilir. Eğer herhangi bir sorun çıkmazsa
     * bu dizge sayı tipine dönüştürülür ve yığına verilir.
     * <p>
     * Örnek olarak
     * <p>
     * "23" sayı -> 23
     * şeklinde bir sayı nesnesi veri yığınına atılır.
     *
     * @param Verilerin alınacağı yığın
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject}
     * tipinde döndürülür. Bu durumda bu type {@link com.batuhanbayrakci.objects.ZyNumber} olacaktır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Yığında eleman yoksa
     *                                                             {@code YığınYetersizElemanHatası} tipinde bir hata döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Eğer argüman sayı veya dizge tipinde değilse bu hata
     *                                                             döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyValueError          Eğer girilen dizge uygun sayı formatında değilse
     *                                                             bu hata fırlatılır. Örnek olarak {@code "54t"} gibi bir giriş, uygun bir
     *                                                             sayı formatı olmadığı için bu hata fırlatılır.
     */
    public static void sayi(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError, ZyValueError {

        ZyObject arg = stack.getArgument();
        Double donusum;

        if (arg instanceof ZyString) {
            try {
                donusum = Double.parseDouble((String) arg.getValue());
                stack.add(new ZyNumber(donusum));
            } catch (NumberFormatException nfe) {
                throw new ZyValueError("Sayı biçimine uygun olmayan bir dizge kullanıldığından " +
                        "doğru dönüşüm yapılamaz: " + arg, SourceMap.getLineOf(arg));
            }

        } else if (arg instanceof ZyNumber) {
            stack.add(arg);
        } else {
            throw new ZyTypeError("sayı isminin çalışabilmesi için argümanın " +
                    "'sayı' veya 'dizge' tipinde olması gerekir. Mevcut type ise bir" +
                    " '" +
                    arg.getType() + "'", SourceMap.getLineOf(arg));

        }
    }

    /**
     * Yığının en üstündeki nesneyi dizge tipine dönüştürüp
     * yığına veren metot.
     * <p>
     * Eğer çekilen argüman dizge ise olduğu gibi yeniden yığına
     * verilir. Yani kopyalanmış gibi olur.
     * <p>
     * Fakat eğer çekilen argüman başka bir tipte ise o tipin dizge
     * gösterimi kullanılır.
     * <p>
     * Örnek olarak
     * <p>
     * [1 2 3] dizge -> "[1 2 3]"
     * şeklinde bir dizge nesnesi veri yığınına atılır.
     *
     * @param Verilerin alınacağı yığın
     * @return İşlem sonucu elde edilen değer, uygun
     * bir {@link com.batuhanbayrakci.objects.ZyObject} tipinde döndürülür. Bu durumda
     * bu type {@link com.batuhanbayrakci.objects.ZyString} olacaktır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Yığında eleman yoksa
     *                                                             {@code YığınYetersizElemanHatası} tipinde bir hata döndürülür.
     */
    public static void dizge(ZyStack stack)
            throws ZyStackUnderflowError {

        ZyObject<?> arg = stack.getArgument();

        if (arg instanceof ZyString) {
            stack.add(new ZyString(((String) arg.getValue())));
        }
        stack.add(new ZyString(arg.toString()));
    }

    /**
     * Yığının en üstündeki nesneyi doğruluk tipine dönüştürüp
     * yığına veren metot.
     * <p>
     * Eğer çekilen argüman 'doğruluk' tipinde ise olduğu gibi yeniden yığına
     * verilir. Yani kopyalanmış gibi olur.
     * <p>
     * Çekilen argüman dizge tipindeyse, eleman sayısına bakılır. Eleman sayısı
     * 0 ise yığına 'y' atılır. Eleman sayısı 0'dan farklı ise yığına 'd' atılır.
     * <p>
     * Çekilen argüman sayı tipindeyse ve 0'dan farklı ise yığına 'd' atılır. Sayı
     * 0 ise yığına 'y' atılır.
     * <p>
     * Çekilen argüman liste tipindeyse, eleman sayısına bakılır. Eleman sayısı
     * 0 ise yığına 'y' atılır. Eleman sayısı 0'dan farklı ise yığına 'd' atılır.
     *
     * @param Verilerin alınacağı yığın
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject}
     * tipinde döndürülür. Bu durumda bu type {@link com.batuhanbayrakci.objects.ZyBoolean} olacaktır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Yığında eleman yoksa
     *                                                             {@code YığınYetersizElemanHatası} tipinde bir hata döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Eğer argüman sayı, dizge, liste veya doğruluk tipinde
     *                                                             değilse bu hata döndürülür.
     */
    public static ZyObject dogruluk(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError {

        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            if (((ZyString) arg).length() > 0) {
                return new ZyBoolean(true);
            }
            return new ZyBoolean(false);
        } else if (arg instanceof ZyNumber) {
            if (((ZyNumber) arg).getValue() == 0) {
                return new ZyBoolean(false);
            } else {
                return new ZyBoolean(true);
            }

        } else if (arg instanceof ZyList) {
            if (((ZyList) arg).size() == 0) {
                return new ZyBoolean(false);
            }
            return new ZyBoolean(true);
        } else if (arg instanceof ZyBoolean) {
            return arg;
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'dogruluk' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }

    /**
     * Yığının en üstündeki nesneyi sabit bir isim tipine dönüştürüp
     * yığına veren metot.
     * <p>
     * Eğer çekilen argüman 'isim' tipinde ise olduğu gibi yeniden yığına
     * verilir. Yani kopyalanmış gibi olur.
     * <p>
     * Çekilen argüman 'dizge' tipindeyse sabit bir isme dönüştürülür ve
     * yığına atılır.
     * <p>
     * Örnek: "batuhan" isim -> /batuhan oluşturulur ve yığına atılır.
     *
     * @param stack Verilerin alınacağı yığın
     * @return İşlem sonucu elde edilen değer, uygun bir {@link com.batuhanbayrakci.objects.ZyObject}
     * tipinde döndürülür. Bu durumda bu type sabit bir {@link com.batuhanbayrakci.objects.ZyName} olacaktır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Yığında eleman yoksa
     *                                                             {@code YığınYetersizElemanHatası} tipinde bir hata döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Eğer argüman isim veya dizge tipinde
     *                                                             değilse bu hata döndürülür.
     */
    public static ZyObject isim(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError {

        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            return ZyName.createLiteral((String) arg.getValue());
        } else if (arg instanceof ZyName) {
            return arg;
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'isim' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }

    /**
     * Yığının en üstündeki nesneyi liste tipine dönüştürüp
     * yığına veren metot.
     * <p>
     * Eğer çekilen argüman 'dizge' ise tüm karakterleri ayrı ayrı
     * ele alarak bir listeye yerleştirilir. Tüm elemanların tipi
     * {@link com.batuhanbayrakci.objects.ZyString} şeklindedir.
     * <p>
     * Çekilen argüman 'liste' tipindeyse nesne olduğu gibi yığına atılır.
     * Yani nesne kopyalanmış olur.
     * <p>
     * Fakat eğer çekilen argüman başka bir tipte ise o tipin dizge
     * gösterimi kullanılır.
     *
     * @param stack Verilerin alınacağı yığın
     * @return İşlem sonucu elde edilen değer, uygun
     * bir {@link com.batuhanbayrakci.objects.ZyObject} tipinde döndürülür. Bu durumda
     * bu type {@link com.batuhanbayrakci.objects.ZyList} olacaktır.
     * @throws com.batuhanbayrakci.exception.ZyStackUnderflowError Yığında eleman yoksa
     *                                                             {@code YığınYetersizElemanHatası} tipinde bir hata döndürülür.
     * @throws com.batuhanbayrakci.exception.ZyTypeError           Eğer argüman 'liste' veya 'dizge' tipinde
     *                                                             değilse bu hata döndürülür.
     */
    public static ZyObject liste(ZyStack stack)
            throws ZyStackUnderflowError, ZyTypeError {

        ZyObject arg = stack.getArgument();

        if (arg instanceof ZyString) {
            char[] karakterler = ((String) arg.getValue()).toCharArray();

            var fff = new ArrayList<ZyObject<?>>();
            for (char c : karakterler) {
                fff.add(new ZyString(Character.toString(c)));
            }
            return new ZyList(fff);
        } else if (arg instanceof ZyList) {
            return arg;
        } else {
            throw new ZyTypeError("'" + arg.getType() + "' nesneleri 'isim' tipine " +
                    "dönüştürülemezler.", SourceMap.getLineOf(arg));
        }
    }

    public static ZyObject elemanYerDegistir(ZyStack stack)
            throws ZyStackUnderflowError {
        List<ZyObject> arg = stack.getArgument(2);
        stack.add(arg.get(0));
        stack.add(arg.get(1));

        return new ZyEmpty();
    }

    /**
     * while döngü karşılığı
     */
    public static ZyObject surece(ZyStack stack) throws ZyError {
        // dongu yapısı

        /*
         * islem  { kosul } sürece
         *
         * önce tekrarlanacak işlem, sonra tekrar koşulu
         */

        List<ZyObject> arg = stack.getArgument(2);
        if (!(arg.get(0) instanceof ZyProcedure)) {
            throw new ZyTypeError("'" + arg.get(0).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(0)));
        }

        ZyProcedure kosul = (ZyProcedure) arg.get(0);
        ZyObject tekrarlanacak = arg.get(1);

        kosul.execute(stack);

        ZyBoolean kontrol = stack.getBooleanArgument();

        while (kontrol.getValue()) {
            tekrarlanacak.execute(stack);

            kosul.execute(stack);

            kontrol = stack.getBooleanArgument();
        }

        return new ZyEmpty();
    }

    /**
     * for döngü karşılığı
     *
     * @throws com.batuhanbayrakci.exception.ZyError
     */
    public static ZyObject yuru(ZyStack stack) throws ZyError {
        // dongu yapısı

        /*
         * bas son aralik { "islem" } yürü
         *
         *
         */

        List<ZyObject> arg = stack.getArgument(4);

        if (!(arg.get(1) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(1).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(1)));
        } else if (!(arg.get(2) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(2).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(2)));
        } else if (!(arg.get(3) instanceof ZyNumber)) {
            throw new ZyTypeError("'" + arg.get(3).getType()
                    + "' tipi, döngü için geçersiz argüman tipidir.", SourceMap.getLineOf(arg.get(3)));
        }

        ZyNumber baslangic = (ZyNumber) arg.get(3);
        ZyNumber son = (ZyNumber) arg.get(2);
        ZyNumber aralik = (ZyNumber) arg.get(1);

        if ((baslangic.getValue() - baslangic.intValue() > 0) ||
                (son.getValue() - son.intValue() > 0) ||
                (aralik.getValue() - aralik.intValue() > 0)) {
            throw new ZyValueError("Döngü sayı değerleri tam sayı olmalıdır.", SourceMap.getLineOf(baslangic));
        }

        if (baslangic.getValue() > son.getValue()) {
            throw new ZyValueError("Döngü 'başlangıç' değeri, 'son' değerinden küçük " +
                    "olmalıdır. Şu an başlangıç değeri: '" + baslangic.getValue() + "' ve son değeri: " +
                    "'" + son.getValue() + "' şeklindedir.", SourceMap.getLineOf(baslangic));
        }

        if (aralik.intValue() == 0) {
            throw new ZyValueError("Döngü aralığı 0'dan farklı bir sayı olmalıdır.", SourceMap.getLineOf(aralik));
        }

        ZyObject tekrarlanacak = arg.get(0);

        for (int i = baslangic.intValue(); i < son.intValue(); i += aralik.intValue()) {
            tekrarlanacak.execute(stack);
        }

        return new ZyEmpty();

    }

    /**
     * dip gerçekleştirimi
     */
    public static ZyObject dip(ZyStack stack)
            throws ZyError {
        List<ZyObject> arg = stack.getArgument(2);

        ZyObject saklanacakNesne = arg.get(1);
        arg.get(0).execute(stack);

        stack.add(saklanacakNesne);
        return new ZyEmpty();
    }

}
