package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ListAddTest {

    private ZyStack stack;
    private ListAdd listAddFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        listAddFunction = new ListAdd();
    }

    @Test
    public void shouldAddElementToEmptyList() {
        ZyList liste = new ZyList(new ArrayList<>());

        stack.push(liste);
        stack.push(new ZyNumber(42));

        listAddFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(liste.getValue()).hasSize(1);
        assertThat(liste.getValue().get(0)).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldAddElementToExistingList() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        liste.add(new ZyNumber(2));

        stack.push(liste);
        stack.push(new ZyNumber(3));

        listAddFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(liste.getValue()).hasSize(3);
        assertThat(liste.getValue().get(2)).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldAddStringElement() {
        ZyList liste = new ZyList(new ArrayList<>());

        stack.push(liste);
        stack.push(new ZyString("test"));

        listAddFunction.process(stack);

        assertThat(liste.getValue()).hasSize(1);
        assertThat(liste.getValue().get(0)).isEqualTo(new ZyString("test"));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotList() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> listAddFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> listAddFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
