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

public class DipTest {

    private Dip dipFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        dipFunction = new Dip();
        stack = new ZyStack();
    }

    @Test
    public void shouldExecuteProcedureAndRestoreValue() {
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(42));

        dipFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldWorkWithDifferentTypes() {
        stack.push(new ZyNumber(100));
        stack.push(new ZyString("saved"));
        stack.push(new ZyNumber(200));

        dipFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyString("saved"));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(200));
    }

    @Test
    public void shouldRestoreBooleanValue() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyBoolean(true));
        stack.push(new ZyNumber(2));

        dipFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasOneElement() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> dipFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> dipFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(20));

        dipFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(20));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

