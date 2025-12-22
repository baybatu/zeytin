package com.batuhanbayrakci;

public class Zeytin {

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            Modes.fileMode(fileName);
        } catch (Exception e) {
            if (args.length == 0) {
                Modes.interactiveMode();
            }
        }

    }

}
