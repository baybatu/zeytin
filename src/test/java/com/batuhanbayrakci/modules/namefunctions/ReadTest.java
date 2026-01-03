package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadTest {

    private ZyStack stack;

    @Before
    public void setUp() {
        stack = new ZyStack();
    }

    @Test
    public void shouldReadInputAndPushToStack() {
        BufferedReader reader = new BufferedReader(new StringReader("test input"));
        Read readFunction = new Read(reader);

        readFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("test input"));
    }

    @Test
    public void shouldReadEmptyLine() {
        BufferedReader reader = new BufferedReader(new StringReader("\n"));
        Read readFunction = new Read(reader);

        readFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString(""));
    }

    @Test
    public void shouldReadMultipleLines() {
        BufferedReader reader = new BufferedReader(new StringReader("line1\nline2"));
        Read readFunction = new Read(reader);

        readFunction.process(stack);
        readFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyString("line2"));
        assertThat(stack.pop()).isEqualTo(new ZyString("line1"));
    }
}
