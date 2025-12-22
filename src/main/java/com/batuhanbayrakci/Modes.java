package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZySyntaxError;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.scanner.Constants;
import com.batuhanbayrakci.scanner.Scanner;
import com.batuhanbayrakci.scanner.Token;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Yorumlayıcı iki kipte çalışır. Bunlar etkileşimli ve dosya kipleridir.
 * Etkileşimli kipte her satır birbirinden ayrı olarak kullanıcıdan satır
 * şeklinde alınır. Alınan bu satır yorumlayıcı tarafından yorumlanır ve
 * sonuçlar ana yığına aktarılır.
 * <p>
 * Dosya kipinde ise belirtilen dosyada bulunan Zeytin kodları çalıştırılır
 * ve sonuçlar yığına aktarılır.
 */
public class Modes {

    /**
     * Yorumlayıcının etkileşimli kipte çalışmasını sağlayan metot.
     * <p>
     * Yazılan her satır diğerlerinden bağımsız olarak yorumlanır ve
     * sonuçlar yığına aktarılır.
     */
    public static void interactiveMode() {
        int curvyBracketBalance = 0;
        int squareBracketBalance = 0;
        boolean error = false;
        String inputSymbol = "";
        String source = "";
        Scanner scanner = new Scanner();
        ObjectProcessor processor = new ObjectProcessor();
        List<ZyObject> objects = new ArrayList<ZyObject>();
        ZyParser parser = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            curvyBracketBalance = 0;
            squareBracketBalance = 0;
            error = false;
            Scanner.setLine(0);
            inputSymbol = String.format("(%s) >>> ", processor.objectCount());
            System.out.print(inputSymbol);
            source = "";
            while (true) {
                curvyBracketBalance = 0;
                squareBracketBalance = 0;

                try {
                    source += " " + br.readLine();
                    int s = Scanner.getLine();
                    s++;
                    Scanner.setLine(s);
                    List<Token> araTokenlar = scanner.tokenize(source);

                    for (Token t : araTokenlar) {
                        switch (t.getType()) {
                            case Constants.OPEN_CURVY_BRACKET:
                                curvyBracketBalance++;
                                break;
                            case Constants.OPEN_SQUARE_BRACKET:
                                squareBracketBalance++;
                                break;
                            case Constants.CLOSE_CURVY_BRACKET:
                                curvyBracketBalance--;
                                break;
                            case Constants.CLOSE_SQUARE_BRACKET:
                                squareBracketBalance--;
                                break;
                        }
                    }

                    // sıfırdan büyük olması, parantez açılmış ama
                    // henüz kapanmamış manasına gelir. Bu parantezin
                    // kapanması için bir tur daha girdi alınır.
                    // Herhangi biri negatifse açılmadan kapanmaya çalışılmış
                    // manasına gelir. Bu hata oluşturur ama hatanın yönetimi
                    // ayıklayıcıya bırakılır.
                    // Aynı şekilde ikisi de 0 ise hata yoktur ve yönetim yine
                    // ayıklayıcıya bırakılır.
                    // Aksi tüm durumlarda girdi almaya devam edilir.
                    if (curvyBracketBalance < 0 || squareBracketBalance < 0) {
                        break;
                    } else if (curvyBracketBalance == 0 && squareBracketBalance == 0) {
                        break;
                    } else
                        continue;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ZySyntaxError zsh) {
                    // tokenize hatasi olusursa
                    System.out.println(zsh.getMessage());
                    error = true;
                    break;
                }
            }

            if (!error) {
                try {
                    parser = new ZyParser(source,
                            scanner.tokenize(source));
                    objects = parser.parse();
                    processor.process(objects);
                } catch (ZySyntaxError e) {
                    System.out.println(e.getMessage());
                }
            }

        }

    }

    public static void fileMode(String file) {
        BufferedReader br = null;
        DataInputStream in = null;
        Scanner scanner = new Scanner();
        ObjectProcessor executor = new ObjectProcessor();
        List<ZyObject> objects = new ArrayList<ZyObject>();
        ZyParser parser = null;
        try {
            FileInputStream fstream = new FileInputStream(file);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            String line = null;
            String source = "";

            while ((line = br.readLine()) != null) {
                source += line + "\n";
            }

            try {
                parser = new ZyParser(source, scanner.tokenize(source));
                objects = parser.parse();
                executor.process(objects);

            } catch (ZySyntaxError e) {
                System.out.println("Söz Dizim Hatası: " + e.getMessage());
            }

            in.close();
            executor.showStack();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException ioe) {
        }

    }
}
