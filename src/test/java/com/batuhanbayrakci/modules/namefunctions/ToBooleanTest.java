package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToBooleanTest {

    private ZyStack stack;
    private ToBoolean toBooleanFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        toBooleanFunction = new ToBoolean();
    }

    @Test
    public void shouldConvertNonEmptyStringToTrue() {
        stack.push(new ZyString("test"));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldConvertEmptyStringToFalse() {
        stack.push(new ZyString(""));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldConvertNonZeroNumberToTrue() {
        stack.push(new ZyNumber(42));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldConvertNegativeNumberToTrue() {
        stack.push(new ZyNumber(-5));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldConvertZeroToFalse() {
        stack.push(new ZyNumber(0));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldConvertNonEmptyListToTrue() {
        ZyList liste = new ZyList(new ArrayList<>());
        liste.add(new ZyNumber(1));
        stack.push(liste);

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldConvertEmptyListToFalse() {
        stack.push(new ZyList(new ArrayList<>()));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldKeepBooleanTrueAsIs() {
        stack.push(new ZyBoolean(true));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(true));
    }

    @Test
    public void shouldKeepBooleanFalseAsIs() {
        stack.push(new ZyBoolean(false));

        toBooleanFunction.process(stack);

        assertThat(stack).hasSize(1);
        assertThat(stack.pop()).isEqualTo(new ZyBoolean(false));
    }

    @Test
    public void shouldThrowZyTypeErrorWhenArgIsNotConvertible() {
        stack.push(ZyName.createLiteral("test"));

        assertThatThrownBy(() -> toBooleanFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> toBooleanFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
