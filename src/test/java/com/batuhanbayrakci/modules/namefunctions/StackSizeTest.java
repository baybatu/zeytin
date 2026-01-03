package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StackSizeTest {

    private StackSize stackSizeFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        stackSizeFunction = new StackSize();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnZeroForEmptyStack() {
        stackSizeFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldReturnOneForSingleElement() {
        stack.push(new ZyNumber(42));

        stackSizeFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldReturnCorrectSizeForMultipleElements() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));
        stack.push(new ZyNumber(3));

        stackSizeFunction.process(stack);

        assertThat(stack).hasSize(4);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldWorkWithMixedTypes() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyString("hello"));
        stack.push(new ZyNumber(3));

        stackSizeFunction.process(stack);

        assertThat(stack).hasSize(4);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        stack.push(new ZyNumber(100));
        stack.push(new ZyNumber(200));

        stackSizeFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(200));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(100));
    }
}

