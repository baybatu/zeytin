package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class StackViewTest {

    private ZyStack stack;
    private StackView stackViewFunction;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @Before
    public void setUp() {
        stack = new ZyStack();
        stackViewFunction = new StackView();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void shouldPrintEmptyStack() {
        stackViewFunction.process(stack);
        
        assertThat(outputStream.toString()).contains("Yığın:");
        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldPrintStackWithElements() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyString("test"));

        stackViewFunction.process(stack);

        assertThat(outputStream.toString()).contains("Yığın:");
        assertThat(stack).hasSize(2);
    }

    @Test
    public void shouldNotModifyStack() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));
        stack.push(new ZyNumber(3));

        stackViewFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));

        System.setOut(originalOut);
    }
}
