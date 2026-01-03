package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import com.batuhanbayrakci.objects.ZyType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypeTest {

    private ZyStack stack;
    private Type typeFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        typeFunction = new Type();
    }

    @Test
    public void shouldReturnTypeOfNumber() {
        stack.push(new ZyNumber(42));

        typeFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isInstanceOf(ZyType.class);
    }

    @Test
    public void shouldReturnTypeOfString() {
        stack.push(new ZyString("test"));

        typeFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isInstanceOf(ZyType.class);
    }

    @Test
    public void shouldReturnTypeOfBoolean() {
        stack.push(new ZyBoolean(true));

        typeFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isInstanceOf(ZyType.class);
    }

    @Test
    public void shouldReturnTypeOfList() {
        stack.push(new ZyList(new ArrayList<>()));

        typeFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isInstanceOf(ZyType.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> typeFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
