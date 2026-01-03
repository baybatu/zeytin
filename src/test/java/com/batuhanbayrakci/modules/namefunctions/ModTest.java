package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModTest {

    private Mod modFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        modFunction = new Mod();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnModuloOfTwoNumbers() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldReturnZeroWhenPerfectlyDivisible() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(5));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldReturnModuloWithNegativeNumbers() {
        stack.push(new ZyNumber(-10));
        stack.push(new ZyNumber(3));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-1));
    }

    @Test
    public void shouldReturnModuloWhenDivisorIsNegative() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(-3));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldReturnModuloWithDecimalNumbers() {
        stack.push(new ZyNumber(10.5));
        stack.push(new ZyNumber(3));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1.5));
    }

    @Test
    public void shouldReturnDividendWhenDivisorIsLarger() {
        stack.push(new ZyNumber(3));
        stack.push(new ZyNumber(10));

        modFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenModuloWithStrings() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        assertThatThrownBy(() -> modFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenModuloWithDifferentTypes() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> modFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenModuloWithBooleans() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        assertThatThrownBy(() -> modFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> modFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> modFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        modFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

