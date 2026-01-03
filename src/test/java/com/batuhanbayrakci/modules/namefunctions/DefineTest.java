package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefineTest {

    private Define defineFunction;
    private ZyStack stack;

    @Before
    public void setUp() {
        defineFunction = new Define();
        stack = new ZyStack();
        ZySymbolStack.INSTANCE.clear();
    }

    @Test
    public void shouldDefineNumberVariable() {
        ZyName name = ZyName.createLiteral("x");
        ZyNumber value = new ZyNumber(42);

        stack.push(name);
        stack.push(value);

        defineFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(ZySymbolStack.INSTANCE.findName(name)).isEqualTo(value);
    }

    @Test
    public void shouldDefineStringVariable() {
        ZyName name = ZyName.createLiteral("mesaj");
        ZyString value = new ZyString("merhaba");

        stack.push(name);
        stack.push(value);

        defineFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(ZySymbolStack.INSTANCE.findName(name)).isEqualTo(value);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotName() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> defineFunction.process(stack)).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackHasOneElement() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> defineFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> defineFunction.process(stack)).isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldPreserveStackOrderAfterExecution() {
        ZyNumber existingElement = new ZyNumber(999);
        stack.push(existingElement);

        ZyName name = ZyName.createLiteral("y");
        ZyNumber value = new ZyNumber(100);

        stack.push(name);
        stack.push(value);

        defineFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(existingElement);
    }
}

