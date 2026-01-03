package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.exception.ZyValueError;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyProcedure;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ForLoopTest {

    private ZyStack stack;
    private ForLoop forLoopFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        forLoopFunction = new ForLoop();
    }

    private ZyProcedure createProcedure(ZyNumber value) {
        ZyProcedure proc = new ZyProcedure();
        proc.add(value);
        return proc;
    }

    @Test
    public void shouldExecuteLoopCorrectNumberOfTimes() {
        ZyProcedure action = createProcedure(new ZyNumber(1));

        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(3));
        stack.push(new ZyNumber(1));
        stack.push(action);

        forLoopFunction.process(stack);

        assertThat(stack).hasSize(3);
    }

    @Test
    public void shouldNotExecuteWhenStartEqualsEnd() {
        ZyProcedure action = createProcedure(new ZyNumber(1));

        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(5));
        stack.push(new ZyNumber(1));
        stack.push(action);

        forLoopFunction.process(stack);

        assertThat(stack).isEmpty();
    }

    @Test
    public void shouldExecuteWithCustomStep() {
        ZyProcedure action = createProcedure(new ZyNumber(1));

        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(2));
        stack.push(action);

        forLoopFunction.process(stack);

        assertThat(stack).hasSize(5);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenStartIsNotNumber() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyString("start"));
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(1));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenEndIsNotNumber() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyNumber(0));
        stack.push(new ZyString("end"));
        stack.push(new ZyNumber(1));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenStepIsNotNumber() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(10));
        stack.push(new ZyString("step"));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyValueErrorWhenStartIsGreaterThanEnd() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(1));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyValueError.class);
    }

    @Test
    public void shouldThrowZyValueErrorWhenStepIsZero() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(0));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyValueError.class);
    }

    @Test
    public void shouldThrowZyValueErrorWhenValuesAreNotIntegers() {
        ZyProcedure action = new ZyProcedure();

        stack.push(new ZyNumber(0.5));
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(1));
        stack.push(action);

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyValueError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(0));
        stack.push(new ZyNumber(10));

        assertThatThrownBy(() -> forLoopFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
