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

public class MinusOperatorTest {

    private MinusOperator minusOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        minusOperator = new MinusOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldSubtractTwoNumbers() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        minusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(7));
    }

    @Test
    public void shouldSubtractAndReturnNegativeResult() {
        stack.push(new ZyNumber(3));
        stack.push(new ZyNumber(10));

        minusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-7));
    }

    @Test
    public void shouldSubtractTwoNegativeNumbers() {
        stack.push(new ZyNumber(-10));
        stack.push(new ZyNumber(-3));

        minusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-7));
    }

    @Test
    public void shouldSubtractWithZero() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(0));

        minusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenSubtractingNumberAndString() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> minusOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenSubtractingStrings() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        assertThatThrownBy(() -> minusOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenSubtractingUnsupportedTypes() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        assertThatThrownBy(() -> minusOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> minusOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> minusOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        minusOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(7));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

