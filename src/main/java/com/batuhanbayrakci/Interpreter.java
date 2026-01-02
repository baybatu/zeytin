package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZySyntaxError;
import com.batuhanbayrakci.scanner.Scanner;

public class Interpreter {

    public static ZyStack interpret(String sourceCode) {
        try {
            var objectProcessor = new ObjectProcessor();
            var scanner = new Scanner();
            var tokens = scanner.tokenize(sourceCode);
            var parser = new ZyParser(sourceCode, tokens);
            var objects = parser.parse();
            objectProcessor.process(objects);
            return objectProcessor.getStack();
        } catch (ZySyntaxError e) {
            System.out.println("Söz Dizim Hatası: " + e.getMessage());
            throw e;
        }
    }
}
