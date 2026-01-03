package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LengthTest {

    private Length lengthFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        lengthFunction = new Length();
        stack = new ZyStack();
    }

    @Test
    public void shouldReturnStringLength() {
        stack.push(new ZyString("merhaba"));

        lengthFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(7));
    }

    @Test
    public void shouldReturnEmptyStringLength() {
        stack.push(new ZyString(""));

        lengthFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldReturnListLength() {
        ZyList list = new ZyList(new ArrayList<>());
        list.add(new ZyNumber(1));
        list.add(new ZyNumber(2));
        list.add(new ZyNumber(3));

        stack.push(list);

        lengthFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldReturnEmptyListLength() {
        ZyList list = new ZyList(new ArrayList<>());

        stack.push(list);

        lengthFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(0));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgumentIsNumber() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> lengthFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgumentIsBoolean() {
        stack.push(new ZyBoolean(true));

        assertThatThrownBy(() -> lengthFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> lengthFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);
        stack.push(new ZyString("test"));

        lengthFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(4));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

