package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FalseTest {

    private False falseFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        falseFunction = new False();
        stack = new ZyStack();
    }

    @Test
    public void shouldPushFalseToEmptyStack() {
        falseFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldPushFalseToNonEmptyStack() {
        stack.push(new ZyNumber(42));

        falseFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        falseFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }
}

