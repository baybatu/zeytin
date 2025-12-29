package com.batuhanbayrakci;

import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.Stack;


/**
 * <code>ZySembolYigin</code> sınıfı, {@link ZySymbolTable} tipi tabloları
 * barındırır. Bu tabloların her biri ayrı bir kapsamı(scope) ifade eder ve bu
 * sayede farklı kapsamlarda aynı isme sahip değişkenler veya prosedürler
 * tanımlanabilir.
 */
public class ZySymbolStack extends Stack<ZySymbolTable> {

    public static final ZySymbolStack INSTANCE = new ZySymbolStack();

    private ZySymbolStack() {
        add(new ZySymbolTable());
    }

    /**
     * Sembol yığınının en üstündeki sembol tablosuna yeni bir girdi ekler.
     * Girdi esasen anahtar-değer ikilisinden oluşur.
     *
     * @param    nameToAdd    sembol yığınının en üstündeki sembol tablosuna
     * eklenecek olan girdinin 'anahtar' ı olan ZyIsim
     * nesnesi
     * @param    objectToAdd sembol yığınının en üstündeki sembol tablosuna
     * eklenecek olan girdinin 'değer' i olan ZyNesne
     * nesnesi
     */
    public void addName(ZyName nameToAdd, ZyObject objectToAdd) {
        lastElement().put(nameToAdd, objectToAdd);
    }

    /**
     * Sembol yığınının en üstün bir alt sembol tablosundan itibaren aşağıya doğru
     * tarama yapar. Tanımlanmış isim varsa onun değerini değiştirir. Yoksa hiçbir şey
     * yapmaz.
     * <p>
     * Girdi esasen anahtar-değer ikilisinden oluşur.
     *
     * @param    nameToBeChanged    sembol tablosunda değiştirilecek girdinin
     * 'anahtar' ı olan ZyIsim nesnesi
     * @param    objectToBeChanged sembol tablosunda değiştirilecek girdinin
     * 'değer' i olan ZyNesne nesnesi
     */
    public boolean changeName(ZyName nameToBeChanged, ZyObject objectToBeChanged) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (get(i).containsKey(nameToBeChanged)) {
                get(i).put(nameToBeChanged, objectToBeChanged);
                return true;
            }
        }
        return false;
    }

    /**
     * Sembol yığınının en üstüne yeni bir sembol tablosu ekler.
     * Tablonun her bir hücresi ZyIsim - ZyNesne ikilisinden oluşur.
     *
     * @param    symbolTable        sembol yığınına eklenecek sembol tablo
     */
    public void addTable(ZySymbolTable symbolTable) {
        add(symbolTable);
    }

    /**
     * Sembol yığınının en üstündeki sembol tablosunu siler.
     */
    public void removeTable() {
        pop();
    }

    /**
     * Sembol yığınında 'aranan' ile belirtilen ismi arar. En üstteki
     * kapsamdan(scope) başlayarak alta doğru tüm kapsamlar taranır.
     *
     * @param name Yığında aranacak isim nesnesi
     * @return Sembol yığınında bulunan ZyNesne'yi döndürür. Aranan isim
     * bulunamazsa null döndürür.
     */
    public ZyObject findName(ZyName name) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.get(i).containsKey(name)) {
                return this.get(i).get(name);
            }
        }
        return null;
    }

}
