package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.exception.ZyValueError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToNumberTest {

    private ZyStack stack;
    private ToNumber toNumberFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        toNumberFunction = new ToNumber();
    }

    @Test
    public void shouldConvertStringToNumber() {
        stack.push(new ZyString("42"));

        toNumberFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldConvertStringWithDecimalToNumber() {
        stack.push(new ZyString("3.14"));

        toNumberFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3.14));
    }

    @Test
    public void shouldConvertNegativeStringToNumber() {
        stack.push(new ZyString("-25"));

        toNumberFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-25));
    }

    @Test
    public void shouldKeepNumberAsIs() {
        stack.push(new ZyNumber(100));

        toNumberFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(100));
    }

    @Test
    public void shouldThrowZyValueErrorWhenStringIsNotValidNumber() {
        stack.push(new ZyString("not a number"));

        assertThatThrownBy(() -> toNumberFunction.process(stack))
                .isInstanceOf(ZyValueError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgIsNotStringOrNumber() {
        stack.push(new ZyBoolean(true));

        assertThatThrownBy(() -> toNumberFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> toNumberFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
