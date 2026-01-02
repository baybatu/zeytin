package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoopTest {

    private Loop loopFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        loopFunction = new Loop();
        stack = new ZyStack();
    }

    @Test
    public void shouldExecuteBlockNTimes() {
        ZyNumber block = new ZyNumber(10);
        ZyNumber repeatCount = new ZyNumber(3);

        stack.push(block);
        stack.push(repeatCount);

        loopFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(10));
    }

    @Test
    public void shouldExecuteBlockOnce() {
        ZyNumber block = new ZyNumber(42);
        ZyNumber repeatCount = new ZyNumber(1);

        stack.push(block);
        stack.push(repeatCount);

        loopFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenCountIsNotNumber() {
        ZyNumber block = new ZyNumber(10);
        ZyBoolean invalidCount = new ZyBoolean(true);

        stack.push(block);
        stack.push(invalidCount);

        assertThatThrownBy(() -> loopFunction.process(stack))
                .isInstanceOf(ZyTypeError.class)
                .hasMessageContaining("döngü için geçersiz argüman tipidir");
    }

    @Test
    public void shouldNotExecuteBlockWhenCountIsZero() {
        ZyNumber block = new ZyNumber(10);
        ZyNumber zeroCount = new ZyNumber(0);

        stack.push(block);
        stack.push(zeroCount);

        loopFunction.process(stack);

        assertThat(stack).hasSize(0);
    }

    @Test
    public void shouldThrowZyErrorWhenCountIsNegative() {
        ZyNumber block = new ZyNumber(10);
        ZyNumber negativeCount = new ZyNumber(-5);

        stack.push(block);
        stack.push(negativeCount);

        assertThatThrownBy(() -> loopFunction.process(stack))
                .isInstanceOf(ZyError.class)
                .hasMessageContaining("tekrar sayısı pozitif olmalıdır");
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasInsufficientElements() {
        stack.push(new ZyNumber(3));

        assertThatThrownBy(() -> loopFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("yeterli eleman yok");
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> loopFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        ZyNumber block = new ZyNumber(42);
        ZyNumber repeatCount = new ZyNumber(2);

        stack.push(block);
        stack.push(repeatCount);

        loopFunction.process(stack);

        assertThat(stack).hasSize(3);
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(new ZyNumber(42));
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

