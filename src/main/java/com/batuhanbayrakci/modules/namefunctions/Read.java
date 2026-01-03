package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Konsoldan girdi okur ve yığına ekler.
 * (S' -> dizge S')
 */
public class Read implements ZyNameFunction {

    private BufferedReader reader;

    public Read() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Read(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void process(ZyStack stack) throws ZyStackUnderflowError, ZyTypeError {
        String read;
        try {
            read = reader.readLine();
            ZyString readObject = new ZyString(read);
            stack.push(readObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

