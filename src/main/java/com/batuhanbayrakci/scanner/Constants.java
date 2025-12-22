package com.batuhanbayrakci.scanner;

public interface Constants {

    int NAME_LITERAL = 1;
    int NAME_EXECUTABLE = 2;
    int NUMBER = 3;
    int OPERATOR = 11;
    int STRING = 4;
    int OPEN_SQUARE_BRACKET = 5;
    int CLOSE_SQUARE_BRACKET = 6;
    int OPEN_CURVY_BRACKET = 7;
    int CLOSE_CURVY_BRACKET = 8;
    int COMMENT = 9;
    int WHITESPACE = 10;
    int NEWLINE = 12;
    int OTHER = 100;

    String[] TR_CHARACTERS = {
            "\u00e7", "\u00c7", "\u011f", "\u011e",
            "\u0131", "\u0130", "\u00f6", "\u00d6",
            "\u00fc", "\u00dc", "\u015f", "\u015e"
    };

//	String KUCUK_C = "\u00e7";
//	String BUYUK_C = "\u00c7";
//	String YUMUSAK_G = "\u011f";
//	String BUYUK_YUMUSAK_G = "\u011e";
//	String KUCUK_I = "\u0131";
//	String BUYUK_I = "\u0130";
//	String KUCUK_O = "\u00f6";
//	String BUYUK_O = "\u00d6";
//	String KUCUK_U = "\u00fc";
//	String BUYUK_U = "\u00dc";
//	String KUCUK_S = "\u015f";
//	String BUYUK_S = "\u015e";

}

