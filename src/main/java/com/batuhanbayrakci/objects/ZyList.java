package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ObjectProcessor;
import com.batuhanbayrakci.ZyStack;

import java.util.List;


/**
 * Farklı tipte nesnelerin bir arada tutulabileceği nesne tipidir.
 * Zeytin listelerinde herhangi bir type kısıtlaması olmadığından dolayı
 * liste içerisinde listeler tutulabilir. Bu sayede matrislerin de
 * kullanımı sağlanmış olur.
 * <p>
 * Her liste esasında bir yığındır. Yani her liste oluşturulduğunda
 * iç tarafta bir yığın oluşturulmuş olur.
 * <p>
 * Bu nesnenin çalıştırılabilirliği(değerlendirilebilirliği) yoktur.
 * Bu da, yorumlayıcının bu nesneyle karşılaştığında nesneyi doğrudan
 * veri yığına atacağı manasına gelir. Bu durumda nesne, bir literal
 * gibi işlem görmüş olur.
 * <p>
 * Örnek kullanım:
 * <p>
 * [1 2 3]
 * <p>
 * [6 5 [5 4] {+}]
 * <p>
 * ["merhaba" "dünya"]
 * <p>
 * Listedeki elemanlar birbirleriyle en az bir boşluk yardımıyla ayrılırlar.
 * Herhangi bir virgül benzeri ayırıcı karaktere ihtiyaç duymazlar.
 */
public class ZyList extends ZyObject<List<ZyObject>> {

    public ZyList(List<ZyObject> value) {
        super(value, false);
        getValue().addAll(value);
    }

    @Override
    public String getType() {
        return "liste";
    }

    @Override
    public void process(ZyStack stack) {
        ObjectProcessor executor = new ObjectProcessor();
        executor.process(getValue());
        setValue(executor.getStack());
        stack.add(this);
    }

    /**
     * Mevcut listenin sonuna {@link ZyObject} tipinde bir nesne ekler.
     * Bu nesne bir liste olabileceği gibi bir prosedür de olabilir.
     * Herhangi bir type kısıtlaması yoktur.
     *
     * @param    zn    Mevcut listenin sonuna eklenecek olan Zeytin nesnesi
     */
    public void add(ZyObject zn) {
        getValue().add(zn);
    }

    /**
     * Bir listenin sonuna bir listeyi ekleyerek mevcut
     * listeyi genişleten metot.
     * <p>
     * {@code ekle(ZyNesne)} metodundan farklı olarak bir elemanı
     * listenin sonuna eklemek yerine mevcut listenin sonuna yerleştirir.
     * <p>
     * Örnek olarak [1 2 3] ile [4 5 6] listeleri bu metoda sokulduklarında
     * sonuç olarak elimizde [1 2 3 4 5 6] gibi bir liste olur.
     *
     * @param    liste    Mevcut listenin sonuna eklenecek olan liste
     */
    public void addAll(ZyList liste) {
        for (ZyObject n : liste.getValue()) {
            getValue().add(n);
        }
    }

    /**
     * Listedeki eleman sayısını verir.
     */
    public int size() {
        return getValue().size();
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
