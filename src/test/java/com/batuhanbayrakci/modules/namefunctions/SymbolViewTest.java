package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolViewTest {

    private ZyStack stack;
    private SymbolView symbolViewFunction;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        stack = new ZyStack();
        symbolViewFunction = new SymbolView();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldPrintSymbolStack() {
        symbolViewFunction.process(stack);
        
        assertThat(outputStream.toString()).contains("Sembol Yığını:");
        
        System.setOut(originalOut);
    }

    @Test
    public void shouldNotModifyStack() {
        symbolViewFunction.process(stack);

        assertThat(stack).isEmpty();
        
        System.setOut(originalOut);
    }
}
