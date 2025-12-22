package com.batuhanbayrakci.scanner;

import com.batuhanbayrakci.exception.ZySyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner implements Constants {

    // Tarayıcının kod içerisindeki satır konumu.
    // Herhangi bir nesneden bağımsızdır.
    private static int line = 0;
    private List<Rule> rules = new ArrayList<Rule>();
    private List<String> operators = new ArrayList<String>();

    public Scanner() {
        loadOperators();
        rules.add(new Rule(NUMBER, "[+-]{0,1}([0-9]+\\.[0-9]*|[0-9]*\\.[0-9]+|[0-9]+)"));
        rules.add(new Rule(NAME_LITERAL, generateNamePattern(true)));
        rules.add(new Rule(OPERATOR, join(operators)));
        rules.add(new Rule(NAME_EXECUTABLE, generateNamePattern(false)));
        rules.add(new Rule(STRING, "\\\"([^\\\\\"]|\\\\\\\\|\\\\.)*\\\""));
        rules.add(new Rule(OPEN_SQUARE_BRACKET, "\\["));
        rules.add(new Rule(CLOSE_SQUARE_BRACKET, "\\]"));
        rules.add(new Rule(OPEN_CURVY_BRACKET, "\\{"));
        rules.add(new Rule(CLOSE_CURVY_BRACKET, "\\}"));
        rules.add(new Rule(COMMENT, "#.*"));
        rules.add(new Rule(NEWLINE, "\\n"));
        rules.add(new Rule(WHITESPACE, "\\s+"));
        rules.add(new Rule(OTHER, ".*"));
    }

    public static int getLine() {
        return line;
    }

    public static void setLine(int _line) {
        line = _line;
    }

    private void loadOperators() {

        this.operators.add("\\+\\+");

        this.operators.add("\\+");
        this.operators.add("\\-");
        this.operators.add("\\*");
        this.operators.add("\\/");

        this.operators.add("\\<\\=");
        this.operators.add("\\=\\!");
        this.operators.add("\\>\\=");

        this.operators.add("\\=");
        this.operators.add("\\<");
        this.operators.add("\\>");

    }

    private String generateNamePattern(boolean constant) {
        String turkishCharacters = "";
        for (String chr : TR_CHARACTERS) {
            turkishCharacters += chr;
        }
        String sonuc = String.format("[_a-zA-Z%s]+[_A-Za-z0-9%s]*",
                turkishCharacters, turkishCharacters);

        if (constant) {
            return "/" + sonuc;
        }
        return sonuc;
    }

    private String join(List<String> listStr) {

        String result = "";
        for (String s : listStr) {
            result += s + "|";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    public List<Token> tokenize(String code) throws ZySyntaxError {
        List<Token> tokens = new ArrayList<Token>();
        int pos = 0;
        //int line = 1;
        final int end = code.length();

        Matcher m = Pattern.compile("").matcher(code);

//		m.useTransparentBounds(true).useAnchoringBounds(false);
        while (pos < end) {
            m.region(pos, end);

            for (Rule rule : rules) {
                if (m.usePattern(rule.pattern).lookingAt()) {
                    if (rule.type == NEWLINE) {
                        line++;
                    }
                    if (rule.type == OTHER) {
                        throw new ZySyntaxError("Geçersiz karakter: " +
                                "'" + code.charAt(m.start()) + "'", line);
                    }
//					System.out.println(kod.substring(m.start(), m.end()));
                    tokens.add(new Token(rule.type, m.start(), m.end(), line));
                    pos = m.end();
                    break; // eslesene kadar
                }
            }
        }
        return tokens;
    }
}
