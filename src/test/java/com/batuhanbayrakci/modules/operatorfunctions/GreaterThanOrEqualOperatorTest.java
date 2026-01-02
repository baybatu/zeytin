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

public class GreaterThanOrEqualOperatorTest {

    private GreaterThanOrEqualOperator greaterThanOrEqualOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        greaterThanOrEqualOperator = new GreaterThanOrEqualOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnTrueWhenFirstIsGreaterThanSecond() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnTrueWhenNumbersAreEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenFirstIsLessThanSecond() {
        stack.push(new ZyNumber(3));
        stack.push(new ZyNumber(10));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldWorkWithNegativeNumbers() {
        stack.push(new ZyNumber(-5));
        stack.push(new ZyNumber(-10));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldWorkWithDecimalNumbers() {
        stack.push(new ZyNumber(2.5));
        stack.push(new ZyNumber(2.5));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenComparingStrings() {
        stack.push(new ZyString("a"));
        stack.push(new ZyString("b"));

        assertThatThrownBy(() -> greaterThanOrEqualOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenComparingDifferentTypes() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> greaterThanOrEqualOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(5));

        assertThatThrownBy(() -> greaterThanOrEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> greaterThanOrEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        greaterThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

