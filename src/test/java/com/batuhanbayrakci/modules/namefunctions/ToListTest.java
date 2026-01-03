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

public class ToListTest {

    private ZyStack stack;
    private ToList toListFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        toListFunction = new ToList();
    }

    @Test
    public void shouldConvertStringToListOfCharacters() {
        stack.push(new ZyString("abc"));

        toListFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue().get(0)).isEqualTo(new ZyString("a"));
        assertThat(result.getValue().get(1)).isEqualTo(new ZyString("b"));
        assertThat(result.getValue().get(2)).isEqualTo(new ZyString("c"));
    }

    @Test
    public void shouldConvertEmptyStringToEmptyList() {
        stack.push(new ZyString(""));

        toListFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).isEmpty();
    }

    @Test
    public void shouldKeepListAsIs() {
        ZyList originalList = new ZyList(new ArrayList<>());
        originalList.add(new ZyNumber(1));
        originalList.add(new ZyNumber(2));
        stack.push(originalList);

        toListFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(originalList);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgIsNotStringOrList() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> toListFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> toListFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
