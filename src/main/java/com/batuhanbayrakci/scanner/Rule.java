package com.batuhanbayrakci.scanner;

import java.util.regex.Pattern;

public class Rule {

    int type;
    Pattern pattern;

    Rule(int type, String regex) {
        this.type = type;
        pattern = Pattern.compile(regex);
    }
}

