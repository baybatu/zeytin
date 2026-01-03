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

public class ListSetTest {

    private ZyStack stack;
    private ListSet listSetFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        listSetFunction = new ListSet();
    }

    @Test
    public void shouldSetFirstElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        liste.add(new ZyNumber(2));
        liste.add(new ZyNumber(3));
        
        stack.push(liste);
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(100));

        listSetFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(liste.getValue().get(0)).isEqualTo(new ZyNumber(100));
    }

    @Test
    public void shouldSetMiddleElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        liste.add(new ZyNumber(2));
        liste.add(new ZyNumber(3));
        
        stack.push(liste);
        stack.push(new ZyNumber(1));
        stack.push(new ZyString("yeni"));

        listSetFunction.process(stack);

        assertThat(liste.getValue().get(1)).isEqualTo(new ZyString("yeni"));
    }

    @Test
    public void shouldSetLastElement() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        liste.add(new ZyNumber(2));
        liste.add(new ZyNumber(3));
        
        stack.push(liste);
        stack.push(new ZyNumber(2));
        stack.push(new ZyNumber(999));

        listSetFunction.process(stack);

        assertThat(liste.getValue().get(2)).isEqualTo(new ZyNumber(999));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotList() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(100));

        assertThatThrownBy(() -> listSetFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenIndexIsNotNumber() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyString("not a number"));
        stack.push(new ZyNumber(100));

        assertThatThrownBy(() -> listSetFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsNegative() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyNumber(-1));
        stack.push(new ZyNumber(100));

        assertThatThrownBy(() -> listSetFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyIndexBoundErrorWhenIndexIsOutOfBounds() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        
        stack.push(liste);
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(100));

        assertThatThrownBy(() -> listSetFunction.process(stack))
                .isInstanceOf(ZyIndexBoundError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(100));

        assertThatThrownBy(() -> listSetFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
