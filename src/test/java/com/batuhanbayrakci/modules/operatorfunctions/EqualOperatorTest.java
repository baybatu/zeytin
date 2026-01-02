package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EqualOperatorTest {

    private EqualOperator equalOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        equalOperator = new EqualOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnTrueWhenNumbersAreEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenNumbersAreNotEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(10));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnTrueWhenStringsAreEqual() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("hello"));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenStringsAreNotEqual() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnTrueWhenBooleansAreEqual() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(true));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenBooleansAreNotEqual() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnFalseWhenComparingDifferentTypes() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyString("5"));

        equalOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(5));

        assertThatThrownBy(() -> equalOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> equalOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        equalOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

