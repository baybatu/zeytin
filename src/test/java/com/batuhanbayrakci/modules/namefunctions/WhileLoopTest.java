package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyProcedure;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WhileLoopTest {

    private ZyStack stack;
    private WhileLoop whileLoopFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        whileLoopFunction = new WhileLoop();
    }

    @Test
    public void shouldNotExecuteWhenConditionIsFalseFromStart() {
        ZyProcedure condition = new ZyProcedure();
        condition.add(new ZyBoolean(false));

        ZyProcedure action = new ZyProcedure();
        action.add(new ZyNumber(42));

        stack.push(action);
        stack.push(condition);

        whileLoopFunction.process(stack);

        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldThrowZyTypeErrorWhenConditionIsNotProcedure() {
        stack.push(new ZyNumber(42));
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> whileLoopFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> whileLoopFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
