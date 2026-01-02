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

public class NotEqualOperatorTest {

    private NotEqualOperator notEqualOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        notEqualOperator = new NotEqualOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnFalseWhenNumbersAreEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnTrueWhenNumbersAreNotEqual() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(10));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenStringsAreEqual() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("hello"));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnTrueWhenStringsAreNotEqual() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnFalseWhenBooleansAreEqual() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(true));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldReturnTrueWhenBooleansAreNotEqual() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldReturnTrueWhenComparingDifferentTypes() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyString("5"));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(5));

        assertThatThrownBy(() -> notEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> notEqualOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(10));

        notEqualOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

