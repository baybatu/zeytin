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

public class LessThanOrEqualOperatorTest {

    private LessThanOrEqualOperator lessThanOrEqualOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        lessThanOrEqualOperator = new LessThanOrEqualOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnTrueWhenFirstIsLessThanSecond() {
        stack.push(new ZyNumber(3));
        stack.push(new ZyNumber(10));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnTrueWhenNumbersAreEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenFirstIsGreaterThanSecond() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(3));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldWorkWithNegativeNumbers() {
        stack.push(new ZyNumber(-10));
        stack.push(new ZyNumber(-5));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldWorkWithDecimalNumbers() {
        stack.push(new ZyNumber(2.5));
        stack.push(new ZyNumber(2.5));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenComparingStrings() {
        stack.push(new ZyString("a"));
        stack.push(new ZyString("b"));

        assertThatThrownBy(() -> lessThanOrEqualOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenComparingDifferentTypes() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> lessThanOrEqualOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(5));

        assertThatThrownBy(() -> lessThanOrEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> lessThanOrEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        lessThanOrEqualOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

