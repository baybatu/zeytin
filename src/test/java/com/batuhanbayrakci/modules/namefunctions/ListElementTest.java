package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyIndexBoundError;
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

public class ListElementTest {

    private ZyStack stack;
    private ListElement listElementFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        listElementFunction = new ListElement();
    }

    @Test
    public void shouldGetFirstElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(10));
        liste.add(new ZyNumber(20));
        liste.add(new ZyNumber(30));
        
        stack.push(liste);
        stack.push(new ZyNumber(0));

        listElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
    }

    @Test
    public void shouldGetMiddleElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyString("a"));
        liste.add(new ZyString("b"));
        liste.add(new ZyString("c"));
        
        stack.push(liste);
        stack.push(new ZyNumber(1));

        listElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("b"));
    }

    @Test
    public void shouldGetLastElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        liste.add(new ZyNumber(2));
        liste.add(new ZyNumber(3));
        
        stack.push(liste);
        stack.push(new ZyNumber(2));

        listElementFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotList() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(0));

        assertThatThrownBy(() -> listElementFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenIndexIsNotNumber() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyString("not a number"));

        assertThatThrownBy(() -> listElementFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsNegative() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyNumber(-1));

        assertThatThrownBy(() -> listElementFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsOutOfBounds() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> listElementFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(0));

        assertThatThrownBy(() -> listElementFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
