package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.Interpreter;
import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyList;
import com.batuhanbayrakci.objects.ZyNumber;
import com.batuhanbayrakci.objects.ZyObject;
import com.batuhanbayrakci.objects.ZyOperator;
import com.batuhanbayrakci.objects.ZyProcedure;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapFnTest {

    private ZyStack stack;
    private MapFn mapFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        mapFunction = new MapFn();
    }

    @Test
    public void shouldMapMultiplyByTwo() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(1));
        listItems.add(new ZyNumber(2));
        listItems.add(new ZyNumber(3));
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(2));
        proc.add(new ZyOperator("*"));

        stack.push(inputList);
        stack.push(proc);

        mapFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).hasSize(3);
        assertThat(result.getValue().get(0)).isEqualTo(new ZyNumber(2));
        assertThat(result.getValue().get(1)).isEqualTo(new ZyNumber(4));
        assertThat(result.getValue().get(2)).isEqualTo(new ZyNumber(6));
    }

    @Test
    public void shouldMapAddOne() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(10));
        listItems.add(new ZyNumber(20));
        listItems.add(new ZyNumber(30));
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(1));
        proc.add(new ZyOperator("+"));

        stack.push(inputList);
        stack.push(proc);

        mapFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).hasSize(3);
        assertThat(result.getValue().get(0)).isEqualTo(new ZyNumber(11));
        assertThat(result.getValue().get(1)).isEqualTo(new ZyNumber(21));
        assertThat(result.getValue().get(2)).isEqualTo(new ZyNumber(31));
    }

    @Test
    public void shouldReturnEmptyListForEmptyInput() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(2));
        proc.add(new ZyOperator("*"));

        stack.push(inputList);
        stack.push(proc);

        mapFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).isEmpty();
    }

    @Test
    public void shouldThrowZyTypeErrorWhenProcedureIsNotProcedure() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(1));
        ZyList inputList = new ZyList(listItems);

        stack.push(inputList);
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> mapFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyTypeErrorWhenListIsNotList() {
        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(2));
        proc.add(new ZyOperator("*"));

        stack.push(new ZyNumber(42));
        stack.push(proc);

        assertThatThrownBy(() -> mapFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenStackIsEmpty() {
        assertThatThrownBy(() -> mapFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldThrowZyStackUnderflowErrorWhenOnlyProcedureOnStack() {
        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(2));
        stack.push(proc);

        assertThatThrownBy(() -> mapFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }

    @Test
    public void shouldWorkWithInterpreter() {
        ZyStack result = Interpreter.interpret("[1 2 3] {2 *} map");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(2));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(4));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(6));
    }

    @Test
    public void shouldWorkWithSquareFunction() {
        ZyStack result = Interpreter.interpret("[2 3 4] {cift *} map");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(4));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(9));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(16));
    }
}
