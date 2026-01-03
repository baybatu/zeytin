package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToNameTest {

    private ZyStack stack;
    private ToName toNameFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        toNameFunction = new ToName();
    }

    @Test
    public void shouldConvertStringToName() {
        stack.push(new ZyString("test"));

        toNameFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(ZyName.createLiteral("test"));
    }

    @Test
    public void shouldKeepNameAsIs() {
        ZyName name = ZyName.createLiteral("original");
        stack.push(name);

        toNameFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(name);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgIsNotStringOrName() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> toNameFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> toNameFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
