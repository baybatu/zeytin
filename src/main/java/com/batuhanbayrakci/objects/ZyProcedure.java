package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.ZySymbolTable;
import com.batuhanbayrakci.exception.ZyError;

import java.util.List;


/**
 * <strong>ZyProsedur</strong>, prosedürlerin gerçekleştirimini
 * sağlamak amacıyla kullanılan bir {@link ZyObject} alt sınıfıdır.
 * <p>
 * Her prosedürün içerisinde ZyNesne nesneleri bulunur ve bu nesneler
 * calistir(ZyYigin) metodu yardımıyla çalıştırılır. Tüm nesneler
 * {@link ZyObject} sınıfından türediği için bir prosedür başka bir prosedür
 * içerisinde bulunabilir. Çünkü prosedürün kendisi de bir ZyObject'dir.
 * Bu sayede iç içe prosedür gerçekleştirimi elde edilmiş olur.
 * <p>
 * Her prosedürün kendine özgü bir kapsamı vardır ve bu kapsam, prosedür
 * çalıştırılırken oluşturulur; çalışma sona ererken silinir.
 * <p>
 * Prosedürün sahip olduğu elemanlar {@link com.batuhanbayrakci.ZyStack} tipinde bir veri yapısında
 * tutulur.
 */
public class ZyProcedure extends ZyObject<List<ZyObject<?>>> {

    /**
     * Prosedürün ilk yüklemesini yapar. Prosedürün iç yığınını oluşturur ve
     * yapılandırır.
     *
     */
    public ZyProcedure() {
        super(new ZyStack());
    }

    @Override
    public String getType() {
        return "prosedur";
    }

    /**
     * Soz konusu prosedüre ait bir kapsam oluşturulur.
     * Ardından ise prosedür içerisindeki tüm {@link ZyObject}
     * tipi nesneler tek tek isletilir.
     *
     * <p>Prosedür sona ermeden önce o prosedüre ait kapsam sembol tabloları yığınından
     * atılır.</p>
     *
     * @throws com.batuhanbayrakci.exception.ZyError
     * @param    stack    Ana yığın
     */
    public void execute(ZyStack stack) throws ZyError {
        ZySymbolTable symbolTableForProc = new ZySymbolTable();
        ZySymbolStack.INSTANCE.addTable(symbolTableForProc);
        for (ZyObject<?> obj : getValue()) {
            obj.process(stack);
        }
        ZySymbolStack.INSTANCE.removeTable();
    }

    /**
     * Prosedürün içerisindeki elemanların yerleştirildiği yığına
     * yeni bir eleman eklemek için kullanılan metot
     *
     * @param    zn    eklenecek {@link ZyObject} tipindeki nesne
     */
    public void add(ZyObject zn) {
        getValue().add(zn);
    }

    /**
     * Prosedürün karakterler biçiminde ifade edilişini sağlar.
     * Prosedürler '{' ve '}' parantezleri arasına yazılır.
     * <p>
     * Prosedüre ait elemanların da karakterler biçimde gösterimini
     * elde eder.
     */
    @Override
    public String toString() {
        String output = "{ ";
        for (ZyObject obj : getValue()) {
            output = output + obj.toString();
            output = output + " ";
        }
        output = output + "}";
        return output;
    }
}
