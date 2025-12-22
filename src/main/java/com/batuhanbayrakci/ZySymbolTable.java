package com.batuhanbayrakci;

import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyObject;

import java.util.HashMap;

/**
 * Sembol tablolarının her biri ayrı bir kapsamı(scope) temsil eder.
 * Sembol tabloları {@link ZySymbolStack} tipinde bir yığında tutulur ve
 * herhangi bir ismin çalıştırılması durumdan başvurulur. Yukarıdan
 * aşağıya anahtarlar incelenir. Eşlenen varsa karşısındaki {@link com.batuhanbayrakci.objects.ZyObject}
 * çalıştırılır.
 * <p>
 * Her prosedür çalıştırılmaya başlarken yeni bir sembol tablosu oluşturulur
 * ve sembol yığınına eklenir. Prosedür bitince ise çıkarılır. Bu sayede
 * yerel değişken kavramı gerçeklenmiş olur.
 */
public class ZySymbolTable extends HashMap<ZyName, ZyObject> {

}
