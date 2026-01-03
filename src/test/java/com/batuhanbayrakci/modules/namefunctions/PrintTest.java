package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintTest {

    private ZyStack stack;
    private Print printFunction;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        stack = new ZyStack();
        printFunction = new Print();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldPrintNumber() {
        stack.push(new ZyNumber(42));

        printFunction.process(stack);

        assertThat(outputStream.toString()).contains("42");
    }

    @Test
    public void shouldPrintString() {
        stack.push(new ZyString("merhaba"));

        printFunction.process(stack);

        assertThat(outputStream.toString()).contains("merhaba");
    }

    @Test
    public void shouldNotRemoveElementFromStack() {
        stack.push(new ZyNumber(42));

        printFunction.process(stack);

        assertThat(stack).hasSize(1);
    }
}
