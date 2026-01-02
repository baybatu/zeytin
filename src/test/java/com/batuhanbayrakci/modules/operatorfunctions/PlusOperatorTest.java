package com.batuhanbayrakci.modules.operatorfunctions;

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

public class PlusOperatorTest {

    private PlusOperator plusOperator;
    private ZyStack stack;

    @Before
    public void setUp() {
        plusOperator = new PlusOperator();
        stack = new ZyStack();
    }

    @Test
    public void shouldAddTwoNumbers() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(5));

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(15));
    }

    @Test
    public void shouldAddTwoNegativeNumbers() {
        stack.push(new ZyNumber(-10));
        stack.push(new ZyNumber(-5));

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(-15));
    }

    @Test
    public void shouldAddPositiveAndNegativeNumbers() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(-3));

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(7));
    }

    @Test
    public void shouldConcatenateTwoStrings() {
        stack.push(new ZyString("Merhaba"));
        stack.push(new ZyString(" Dünya"));

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString("Merhaba Dünya"));
    }

    @Test
    public void shouldConcatenateTwoEmptyStrings() {
        stack.push(new ZyString(""));
        stack.push(new ZyString(""));

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyString(""));
    }

    @Test
    public void shouldConcatenateTwoLists() {
        ZyList list1 = new ZyList(new ArrayList<>());
        list1.add(new ZyNumber(1));
        list1.add(new ZyNumber(2));

        ZyList list2 = new ZyList(new ArrayList<>());
        list2.add(new ZyNumber(3));
        list2.add(new ZyNumber(4));

        stack.push(list1);
        stack.push(list2);

        plusOperator.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenAddingNumberAndString() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("test"));

        assertThatThrownBy(() -> plusOperator.process(stack))
                .isInstanceOf(ZyTypeError.class)
                .hasMessageContaining("operatörü");
    }

    @Test
    public void shouldThrowZyTypeErrorWhenAddingStringAndNumber() {
        stack.push(new ZyString("test"));
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> plusOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenAddingUnsupportedTypes() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyBoolean(false));

        assertThatThrownBy(() -> plusOperator.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> plusOperator.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("yeterli eleman yok");
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> plusOperator.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(5));

        plusOperator.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(15));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

