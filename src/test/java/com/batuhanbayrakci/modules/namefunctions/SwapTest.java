package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SwapTest {

    private Swap swapFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        swapFunction = new Swap();
        stack = new ZyStack();
    }

    @Test
    public void shouldSwapTwoNumbers() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        swapFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
    }

    @Test
    public void shouldSwapTwoStrings() {
        stack.push(new ZyString("first"));
        stack.push(new ZyString("second"));

        swapFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyString("first"));
        assertThat(stack.pop()).isEqualTo(new ZyString("second"));
    }

    @Test
    public void shouldSwapTwoBooleans() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        swapFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldSwapDifferentTypes() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyString("hello"));

        swapFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(new ZyString("hello"));
    }

    @Test
    public void shouldSwapSameValues() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        swapFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(5));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(5));
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasOneElement() {
        stack.push(new ZyNumber(1));

        assertThatThrownBy(() -> swapFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> swapFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        swapFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

