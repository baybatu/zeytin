package com.batuhanbayrakci;

import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyString;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ZyStackTest {

    private ZyStack stack;

    @Before
    public void setUp() {
        stack = new ZyStack();
    }

    @Test
    public void getArgument_shouldPopElementFromStack() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));

        ZyObject result = stack.getArgument();

        assertThat(result).isEqualTo(new ZyNumber(2));
        assertThat(stack).hasSize(1);
        assertThat(stack.peek()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void getArgument_shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> stack.getArgument())
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("Gerekli argüman sayısı: 1")
                .hasMessageContaining("Mevcut eleman sayısı: 0");
    }

    @Test
    public void getArgument_shouldWorkWithDifferentTypes() {
        stack.push(new ZyString("hello"));
        assertThat(stack.getArgument()).isEqualTo(new ZyString("hello"));

        stack.push(new ZyBoolean(true));
        assertThat(stack.getArgument()).isEqualTo(new ZyBoolean(true));

        stack.push(new ZyNumber(3.14));
        assertThat(stack.getArgument()).isEqualTo(new ZyNumber(3.14));
    }

    @Test
    public void getArgumentWithCount_shouldPopElementsFromStack() {
        stack.push(new ZyNumber(1));
        stack.push(new ZyNumber(2));
        stack.push(new ZyNumber(3));

        List<ZyObject> result = stack.getArgument(2);

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(new ZyNumber(3));
        assertThat(result.get(1)).isEqualTo(new ZyNumber(2));
        assertThat(stack).hasSize(1);
        assertThat(stack.peek()).isEqualTo(new ZyNumber(1));
    }

    @Test
    public void getArgumentWithCount_shouldReturnEmptyListWhenCountIsZero() {
        stack.push(new ZyNumber(1));

        List<ZyObject> result = stack.getArgument(0);

        assertThat(result).isEmpty();
        assertThat(stack).hasSize(1);
    }

    @Test
    public void getArgumentWithCount_shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(1));

        assertThatThrownBy(() -> stack.getArgument(3))
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("Gerekli argüman sayısı: 3")
                .hasMessageContaining("Mevcut eleman sayısı: 1");
    }

    @Test
    public void getArgumentWithCount_shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> stack.getArgument(1))
                .isInstanceOf(ZyStackUnderflowError.class)
                .hasMessageContaining("Gerekli argüman sayısı: 1")
                .hasMessageContaining("Mevcut eleman sayısı: 0");
    }

    @Test
    public void getArgumentWithCount_shouldReturnElementsInPoppedOrder() {
        stack.push(new ZyString("first"));
        stack.push(new ZyNumber(2));
        stack.push(new ZyBoolean(true));

        List<ZyObject> result = stack.getArgument(3);

        assertThat(result.get(0)).isEqualTo(new ZyBoolean(true));
        assertThat(result.get(1)).isEqualTo(new ZyNumber(2));
        assertThat(result.get(2)).isEqualTo(new ZyString("first"));
    }

    @Test
    public void getBooleanArgument_shouldReturnTrueBoolean() {
        stack.push(new ZyBoolean(true));

        ZyBoolean result = stack.getBooleanArgument();

        assertThat(result.getValue()).isTrue();
        assertThat(stack).isEmpty();
    }

    @Test
    public void getBooleanArgument_shouldReturnFalseBoolean() {
        stack.push(new ZyBoolean(false));

        ZyBoolean result = stack.getBooleanArgument();

        assertThat(result.getValue()).isFalse();
        assertThat(stack).isEmpty();
    }

    @Test
    public void getBooleanArgument_shouldThrowZyTypeErrorWhenTopIsNumber() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> stack.getBooleanArgument()).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void getBooleanArgument_shouldThrowZyTypeErrorWhenTopIsString() {
        stack.push(new ZyString("true"));

        assertThatThrownBy(() -> stack.getBooleanArgument()).isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void getBooleanArgument_shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> stack.getBooleanArgument()).isInstanceOf(ZyStackUnderflowError.class);
    }
}
