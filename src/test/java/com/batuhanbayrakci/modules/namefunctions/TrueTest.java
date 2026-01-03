package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrueTest {

    private True trueFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        trueFunction = new True();
        stack = new ZyStack();
    }

    @Test
    public void shouldPushTrueToEmptyStack() {
        trueFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldPushTrueToNonEmptyStack() {
        stack.push(new ZyNumber(42));

        trueFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        trueFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }
}

