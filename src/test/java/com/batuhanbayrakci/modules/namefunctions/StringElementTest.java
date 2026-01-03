package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyIndexBoundError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringElementTest {

    private ZyStack stack;
    private StringElement stringElementFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        stringElementFunction = new StringElement();
    }

    @Test
    public void shouldGetFirstCharacter() {
        stack.push(new ZyString("merhaba"));
        stack.push(new ZyNumber(0));

        stringElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("m"));
    }

    @Test
    public void shouldGetMiddleCharacter() {
        stack.push(new ZyString("merhaba"));
        stack.push(new ZyNumber(3));

        stringElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("h"));
    }

    @Test
    public void shouldGetLastCharacter() {
        stack.push(new ZyString("merhaba"));
        stack.push(new ZyNumber(6));

        stringElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("a"));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotString() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(0));

        assertThatThrownBy(() -> stringElementFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenIndexIsNotNumber() {
        stack.push(new ZyString("test"));
        stack.push(new ZyString("not a number"));

        assertThatThrownBy(() -> stringElementFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsNegative() {
        stack.push(new ZyString("test"));
        stack.push(new ZyNumber(-1));

        assertThatThrownBy(() -> stringElementFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsOutOfBounds() {
        stack.push(new ZyString("test"));
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> stringElementFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> stringElementFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
