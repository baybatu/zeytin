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

public class IncrementOperatorTest {

    private IncrementOperator incrementOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        incrementOperator = new IncrementOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldIncrementPositiveNumber() {
        stack.push(new ZyNumber(5));

        incrementOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(6));
    }

    @Test
    public void shouldIncrementZero() {
        stack.push(new ZyNumber(0));

        incrementOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldIncrementNegativeNumber() {
        stack.push(new ZyNumber(-5));

        incrementOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-4));
    }

    @Test
    public void shouldIncrementDecimalNumber() {
        stack.push(new ZyNumber(2.5));

        incrementOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3.5));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenIncrementingString() {
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> incrementOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenIncrementingBoolean() {
        stack.push(new ZyBoolean(true));

        assertThatThrownBy(() -> incrementOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> incrementOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(5));

        incrementOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(6));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

