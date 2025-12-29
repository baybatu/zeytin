package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZySyntaxError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyOperator;
import com.batuhanbayrakci.objects.ZyProcedure;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.scanner.Constants;
import com.batuhanbayrakci.scanner.Token;
import com.batuhanbayrakci.sourcemap.SourceMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ZyParser implements Constants {

    /**
     * Tarayıcının verdiği token'ları birkaç kurala göre kontrol eder ve uygun
     * Zeytin nesnelerini oluşturur.
     * <p>
     * Önemli bir kısımdır çünkü bundan sonraki aşama bu nesnelerin
     * çalıştırılmasıdır.
     * <p>
     * Kontrol için fazla kural yoktur.
     * <p>
     * Örneğin:
     * <p>
     * 5 4 + örneğinde 4'ten sonra + ile arada asgari bir boşluk(whitespace)
     * olmalıdır. Eğer kontrol etmezsek:
     * <p>
     * 5 4+ 'ü geçerli kabul eder ki bu durumda:
     * <p>
     * +5+5+ gibi bir diziyi de kabul eder ki bu okuması zor bir koda imkan
     * vermek anlamına gelir. Onun için bu basit kurallar faydalıdır.
     */
    private final List<Token> tokens;
    private final String code;

    ZyParser(String code, List<Token> tokens) {
        this.tokens = tokens;
        this.code = code;
    }

    /**
     * Nesneler arası en az bir boşluğun olması gerekir. Fakat istisna durumlar
     * vardır. Bunlar -şimdilik- liste ve prosedür parantezleridir. Çünkü bunlar
     * diğer nesnelerle yapışabilirler.
     * <p>
     * Fakat! 2[ gibi bir durum yanlıştır. 2] doğrudur. Bu gibi durumlar vardır
     * <p>
     * Parantez eşleme bu fonksiyonun işi değildir.
     */
    private Token paranthesisControl() {
        Stack<Integer> parantezKontrol = new Stack<>();
        Token mevcutToken = new Token(-1, -1, -1, -1);

        for (Token t : this.tokens) {
            mevcutToken = t;
            switch (t.getType()) {

                case OPEN_SQUARE_BRACKET:
                    parantezKontrol.add(OPEN_SQUARE_BRACKET);
                    break;

                case CLOSE_SQUARE_BRACKET:
                    if (parantezKontrol.isEmpty()) {
                        return t;
                    } else {
                        if (parantezKontrol.getLast() == OPEN_SQUARE_BRACKET) {
                            parantezKontrol.pop();
                        } else {
                            return t;
                        }
                    }
                    break;

                case OPEN_CURVY_BRACKET:
                    parantezKontrol.add(OPEN_CURVY_BRACKET);
                    break;

                case CLOSE_CURVY_BRACKET:
                    if (parantezKontrol.isEmpty()) {
                        return t;
                    } else {
                        if (parantezKontrol.getLast() == OPEN_CURVY_BRACKET) {
                            parantezKontrol.pop();
                        } else {
                            return t;
                        }
                    }

                    break;

            }

        }

        if (parantezKontrol.isEmpty()) {
            return new Token(-1, -1, -1, -1); // söz dizim hatasız!
        } else {
            return mevcutToken;
        }
    }

    private Token positionControl() {
        Stack<Token> konumYigini = new Stack<>();
        int solEleman;

        for (Token t : this.tokens) {
            if (konumYigini.isEmpty()) {
                konumYigini.add(t);
            } else {
                switch (t.getType()) {

                    case OPERATOR:
                    case NUMBER:
                    case STRING:
                    case NAME_EXECUTABLE:
                    case NAME_LITERAL:
                    case OPEN_SQUARE_BRACKET:
                    case OPEN_CURVY_BRACKET:
                        solEleman = konumYigini.lastElement().getType();
                        if (solEleman == WHITESPACE
                                || solEleman == OPEN_CURVY_BRACKET
                                || solEleman == OPEN_SQUARE_BRACKET
                                || solEleman == NEWLINE) {
                            konumYigini.add(t);
                        } else {
                            return t;
                        }
                        break;

                    default:
                        konumYigini.add(t);
                        break;
                }

            }

        }
        return new Token(-1, -1, -1, -1); // problem yok
    }

    /**
     * Dizgelerdeki ters bölü olayını kontrol eden metot.
     * <p>
     * Yeni satır, backspace gibi özel karakterler burada kontrol
     * edilmez. Burada daha çok \\, \" gibi özel durumlar ele alınır.
     * <p>
     * Bu işlemi gerçekleştirmek için bir yığın kullanılır. Bu yığınla
     * gerekli kontroller gerçekleştirilir.
     * <p>
     * \\ ile karşılaşılırsa bu \ olarak ekrana yazdırılır. Aynı şekilde
     * tırnak işaretini dizge sonu tırnaktan ayırmak için de \" şeklinde
     * kullanmak gereklidir.
     *
     * @return Herhangi bir dizge hatası oluşmazsa tüm değerleri -1 olan
     * bir {@link Token} nesnesi döndürülür. Fakat bir hata oluşursa
     * hatayı sebep olan {@link Token} nesnesi döndürülür.
     */

    private Token stringControl() {
        Stack<String> stringStack = new Stack<>();

        for (Token token : this.tokens) {
            if (token.getType() == STRING) {
                String str = this.code.substring(token.getStart(), token.getEnd());
                String[] d = str.substring(1, str.length() - 1).split("");
                for (String s : d) {
                    // eğer sadece yalın " varsa hata ver
                    if (stringStack.isEmpty()) {
                        if (s.equals("\"")) {
                            return token;
                        }
                    }
                    // önceden eklenmiş \ karakteri var mı ?
                    if (!stringStack.isEmpty()) {
                        if (s.equals("\"")) {
                            // yani " yakalandı
                            stringStack.pop();
                        } else if (s.equals("\\")) {
                            // yani \\ yakalandı
                            stringStack.pop();
                            // asagidaki if'e girmemek icin yukari cik
                            continue;
                        } else {
                            // \ karakterinden sonra farklı bir şey varsa
                            stringStack.pop();
                        }
                    }

                    if (s.equals("\\")) {
                        stringStack.add(s);
                    }
                }

                // eğer yığında \ karakteri kalırsa demek ki eşleşmeyen \ karakteri
                // vardır ki bu da hata demektir.
                if (!stringStack.isEmpty()) {
                    return token;
                }

            }

        }
        return new Token(-1, -1, -1, -1);
    }

    /**
     * Tarayıcının ürettiği tokenları anlamlandırmak için kullanılan metot.
     * <p>
     * Tarayıcının ürettiği tokens belli kurallara göre kontrol edilirler.
     * Bu kurallar zamanla genişleyebilir fakat şu an için şu kontroller yapılır:
     *
     * <ol>
     * <li>Dizgeler için kaçış karakterlerinin kontrolleri</li>
     * <li>Parantezlerin birbirini karşılayıp karşılamadığının kontrolü</li>
     * <li>Nesnelerin belli kurallara göre birbirinden en az bir boşluk ile
     * ayrılıp ayrılmadığının kontrolü</li>
     * </ol>
     *
     * @return Eğer herhangi bir ayıklama hatası oluşmazsa {@link com.batuhanbayrakci.objects.ZyObject} tipinde
     * nesneleri barındıran bir {@link java.util.List} nesnesi döndürülür.
     * @throws com.batuhanbayrakci.exception.ZySyntaxError
     */
    public List<ZyObject<?>> parse() throws ZySyntaxError {
        Token token;

        token = stringControl();
        if (!(token.getStart() == -1)) {
            throw new ZySyntaxError("Dizge sonu karakter hatası", token.getLine());
        }

        token = paranthesisControl();
        if (!(token.getStart() == -1)) {
            throw new ZySyntaxError("Dengesiz parantez >> '" +
                    this.code.charAt(token.getStart()) + "' <<", token.getLine());
        }

        token = positionControl();
        if (!(token.getStart() == -1)) {
            throw new ZySyntaxError("Bitişik nesne >> '" +
                    this.code.charAt(token.getStart()) + "' <<", token.getLine());
        }

        return createObjects();
    }

    public List<ZyObject<?>> createObjects() {

        List<ZyObject<?>> objectList = new ArrayList<>();

        // control stack for assuring of correctness of list and procedure definitions
        List<ZyObject<?>> tempStack = new Stack<>();

        for (Token token : this.tokens) {
            switch (token.getType()) {

                case NAME_LITERAL:
                    ZyName object = ZyName.createLiteral(this.code.substring(token.getStart() + 1, token.getEnd()));
                    SourceMap.addObject(object, token.getLine());

                    if (!tempStack.isEmpty()) {
                        tempStack.getLast().add(object);
                    } else {
                        objectList.add(object);
                    }

                    break;

                case NAME_EXECUTABLE:
                    ZyName executableName = ZyName.createExecutable(this.code.substring(token.getStart(), token.getEnd()));
                    SourceMap.addObject(executableName, token.getLine());

                    if (!tempStack.isEmpty()) {
                        tempStack.getLast().add(executableName);
                    } else {
                        objectList.add(executableName);
                    }

                    break;

                case NUMBER:
                    ZyNumber number = new ZyNumber(
                            Double.parseDouble(this.code.substring(token.getStart(), token.getEnd())));
                    SourceMap.addObject(number, token.getLine());

                    if (!tempStack.isEmpty()) {
                        tempStack.getLast().add(number);
                    } else {
                        objectList.add(number);
                    }

                    break;

                case OPERATOR:
                    ZyOperator operator = new ZyOperator(this.code.substring(token.getStart(), token.getEnd()));
                    SourceMap.addObject(operator, token.getLine());

                    if (!tempStack.isEmpty()) {
                        tempStack.getLast().add(operator);
                    } else {
                        objectList.add(operator);
                    }

                    break;

                case STRING:
                    ZyString string = new ZyString(this.code.substring(token.getStart() + 1, token.getEnd() - 1));
                    SourceMap.addObject(string, token.getLine());

                    if (!tempStack.isEmpty()) {
                        tempStack.getLast().add(string);
                    } else {
                        objectList.add(string);
                    }

                    break;

                case OPEN_SQUARE_BRACKET:
                    ZyList list = new ZyList(new ArrayList<>());
                    tempStack.add(list);
                    break;

                case CLOSE_SQUARE_BRACKET:
                    if (tempStack.size() > 1) {
                        ZyObject<?> alt = tempStack.getLast();
                        tempStack.removeLast();
                        tempStack.getLast().add(alt);
                        SourceMap.addObject(alt, token.getLine());
                    } else {
                        ZyObject<?> alt = tempStack.getLast();
                        objectList.add(alt);
                        tempStack.removeLast();
                        SourceMap.addObject(alt, token.getLine());
                    }
                    break;

                case OPEN_CURVY_BRACKET:
                    ZyProcedure procedure = new ZyProcedure();
                    tempStack.add(procedure);
                    break;

                case CLOSE_CURVY_BRACKET:

                    if (tempStack.size() > 1) {
                        ZyObject<?> alt = tempStack.getLast();
                        tempStack.removeLast();
                        tempStack.getLast().add(alt);
                        SourceMap.addObject(alt, token.getLine());
                    } else {
                        ZyObject<?> alt = tempStack.getLast();
                        objectList.add(alt);
                        tempStack.removeLast();
                        SourceMap.addObject(alt, token.getLine());
                    }
                    break;

                case COMMENT:
                    break;

                case WHITESPACE:
                    // bosluk...
            }
        }
        return objectList;
    }
}
