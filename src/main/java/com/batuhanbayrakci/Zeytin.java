package com.batuhanbayrakci;

public class Zeytin {

    public static void main(String[] args) {
        if (args.length == 1) {
            String fileName = args[0];
            Modes.fileMode(fileName);
        } else if (args.length == 0) {
            Modes.interactiveMode();
        } else {
            System.out.println("Usage: java -jar zeytin.jar [filename]");
        }
    }

}
