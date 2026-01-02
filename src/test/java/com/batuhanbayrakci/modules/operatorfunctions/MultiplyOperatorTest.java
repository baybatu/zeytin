package com.batuhanbayrakci.modules.operatorfunctions;

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

public class MultiplyOperatorTest {

    private MultiplyOperator multiplyOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        multiplyOperator = new MultiplyOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldMultiplyTwoNumbers() {
        stack.push(new ZyNumber(6));
        stack.push(new ZyNumber(7));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldMultiplyWithZero() {
        stack.push(new ZyNumber(100));
        stack.push(new ZyNumber(0));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldMultiplyWithOne() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(1));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldMultiplyNegativeNumbers() {
        stack.push(new ZyNumber(-5));
        stack.push(new ZyNumber(-4));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(20));
    }

    @Test
    public void shouldMultiplyPositiveAndNegativeNumbers() {
        stack.push(new ZyNumber(-5));
        stack.push(new ZyNumber(4));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-20));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenMultiplyingNumberAndString() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> multiplyOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenMultiplyingStrings() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        assertThatThrownBy(() -> multiplyOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenMultiplyingUnsupportedTypes() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        assertThatThrownBy(() -> multiplyOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> multiplyOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> multiplyOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(6));
        stack.push(new ZyNumber(7));

        multiplyOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

