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

public class PopTest {

    private Pop popFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        popFunction = new Pop();
        stack = new ZyStack();
    }

    @Test
    public void shouldRemoveTopElement() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        popFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldRemoveOnlyElement() {
        stack.push(new ZyNumber(42));

        popFunction.process(stack);

        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldRemoveStringElement() {
        stack.push(new ZyString("hello"));

        popFunction.process(stack);

        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldRemoveBooleanElement() {
        stack.push(new ZyBoolean(true));

        popFunction.process(stack);

        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> popFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveRemainingElements() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));
        stack.push(new ZyNumber(3));

        popFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }
}

