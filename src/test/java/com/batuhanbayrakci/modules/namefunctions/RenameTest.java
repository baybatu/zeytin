package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.ZySymbolStack;
import com.batuhanbayrakci.ZySymbolTable;
import com.batuhanbayrakci.exception.ZyNameError;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyName;
import com.batuhanbayrakci.objects.ZyNumber;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RenameTest {

    private ZyStack stack;
    private Rename renameFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        renameFunction = new Rename();
        ZySymbolStack.INSTANCE.addTable(new ZySymbolTable());
    }

    @Test
    public void shouldRenameExistingVariable() {
        ZyName isim = ZyName.createLiteral("x");
        ZySymbolStack.INSTANCE.addName(isim, new ZyNumber(10));
        
        stack.push(isim);
        stack.push(new ZyNumber(42));

        renameFunction.process(stack);

        assertThat(stack).isEmpty();
        assertThat(ZySymbolStack.INSTANCE.findName(isim)).isEqualTo(new ZyNumber(42));
        
        ZySymbolStack.INSTANCE.removeTable();
    }

    @Test
    public void shouldThrowZyTypeErrorWhenFirstArgIsNotName() {
        stack.push(new ZyNumber(10));
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> renameFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyNameErrorWhenNameNotFound() {
        stack.push(ZyName.createLiteral("undefined"));
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> renameFunction.process(stack))
                .isInstanceOf(ZyNameError.class);
        
        ZySymbolStack.INSTANCE.removeTable();
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenNotEnoughElements() {
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> renameFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
