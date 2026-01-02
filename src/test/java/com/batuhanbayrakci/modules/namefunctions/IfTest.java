package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IfTest {

    private If ifFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        ifFunction = new If();
        stack = new ZyStack();
    }

    @Test
    public void shouldExecuteThenBranchWhenConditionIsTrue() {
        ZyBoolean condition = new ZyBoolean(true);
        ZyNumber thenValue = new ZyNumber(31);
        ZyNumber elseValue = new ZyNumber(99);

        stack.push(condition);
        stack.push(thenValue);
        stack.push(elseValue);

        ifFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(thenValue);
    }

    @Test
    public void shouldExecuteElseBranchWhenConditionIsFalse() {
        ZyBoolean condition = new ZyBoolean(false);
        ZyNumber thenValue = new ZyNumber(31);
        ZyNumber elseValue = new ZyNumber(99);

        stack.push(condition);
        stack.push(thenValue);
        stack.push(elseValue);

        ifFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(elseValue);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenConditionIsNotBoolean() {
        ZyNumber invalidCondition = new ZyNumber(1);
        ZyNumber thenValue = new ZyNumber(31);
        ZyNumber elseValue = new ZyNumber(99);

        stack.push(invalidCondition);
        stack.push(thenValue);
        stack.push(elseValue);

        assertThatThrownBy(() -> ifFunction.process(stack))
                .isInstanceOf(ZyTypeError.class)
                .hasMessageContaining("koşul için geçersiz argüman tipidir");
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyBoolean(true));
        stack.push(new ZyNumber(31));

        assertThatThrownBy(() -> ifFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("yeterli eleman yok");
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> ifFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        ZyBoolean condition = new ZyBoolean(true);
        ZyNumber thenValue = new ZyNumber(31);
        ZyNumber elseValue = new ZyNumber(99);

        stack.push(condition);
        stack.push(thenValue);
        stack.push(elseValue);

        ifFunction.process(stack);

        assertThat(stack).hasSize(2);
        assertThat(stack.pop()).isEqualTo(thenValue);
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}
