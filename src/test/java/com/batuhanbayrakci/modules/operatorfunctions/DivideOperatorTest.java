package com.batuhanbayrakci.modules.operatorfunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyDivisionByZeroError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DivideOperatorTest {

    private DivideOperator divideOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        divideOperator = new DivideOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldDivideTwoNumbers() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(2));

        divideOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(5));
    }

    @Test
    public void shouldDivideAndReturnDecimalResult() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(4));

        divideOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2.5));
    }

    @Test
    public void shouldDivideNegativeNumbers() {
        stack.push(new ZyNumber(-20));
        stack.push(new ZyNumber(-4));

        divideOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(5));
    }

    @Test
    public void shouldDividePositiveByNegative() {
        stack.push(new ZyNumber(20));
        stack.push(new ZyNumber(-4));

        divideOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-5));
    }

    @Test
    public void shouldDivideZeroByNumber() {
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(5));

        divideOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldThrowZyDivisionByZeroErrorWhenDividingByZero() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(0));

        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyDivisionByZeroError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenDividingNumberAndString() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenDividingStrings() {
        stack.push(new ZyString("hello"));
        stack.push(new ZyString("world"));

        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenDividingUnsupportedTypes() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> divideOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(20));
        stack.push(new ZyNumber(4));

        divideOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(5));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

