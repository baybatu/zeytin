package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.Interpreter;
import com.batuhanbayrakci.ZyStack;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IfProgramTest {

    @Test
    public void shouldExecuteThenBranchWhenConditionIsTrue() {
        ZyStack stack = Interpreter.interpret("d 31 99 eger");

        assertThat(stack).hasSize(1);
        assertThat(stack.pop().getValue()).isEqualTo(31.0);
    }
}