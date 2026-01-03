package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DupTest {

    private Dup dupFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        dupFunction = new Dup();
        stack = new ZyStack();
    }

    @Test
    public void shouldDuplicateNumber() {
        stack.push(new ZyNumber(42));

        dupFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldDuplicateString() {
        stack.push(new ZyString("hello"));

        dupFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyString("hello"));
        assertThat(stack.pop()).isEqualTo(new ZyString("hello"));
    }

    @Test
    public void shouldDuplicateBoolean() {
        stack.push(new ZyBoolean(true));

        dupFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldDuplicateTopElementOnly() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        dupFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(2));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> dupFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyNumber(42));

        dupFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

