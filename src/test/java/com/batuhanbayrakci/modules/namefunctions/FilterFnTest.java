package com.batuhanbayrakci.modules.namefunctions;

import com.batuhanbayrakci.Interpreter;
import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyStackUnderflowError;
import com.batuhanbayrakci.exception.ZyTypeError;
import com.batuhanbayrakci.objects.ZyBoolean;
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

public class FilterFnTest {

    private ZyStack stack;
    private FilterFn filterFunction;

    @Before
    public void setUp() {
        stack = new ZyStack();
        filterFunction = new FilterFn();
    }

    @Test
    public void shouldFilterWithLessThan() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(1));
        listItems.add(new ZyNumber(2));
        listItems.add(new ZyNumber(3));
        listItems.add(new ZyNumber(4));
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(3));
        proc.add(new ZyOperator("<"));

        stack.push(inputList);
        stack.push(proc);

        filterFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).hasSize(2);
        assertThat(result.getValue().get(0)).isEqualTo(new ZyNumber(1));
        assertThat(result.getValue().get(1)).isEqualTo(new ZyNumber(2));
    }

    @Test
    public void shouldFilterWithLessThanOrEqual() {
        ZyStack result = Interpreter.interpret("[1 2 3 4] {3 <=} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(1));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(2));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(3));
    }

    @Test
    public void shouldFilterWithGreaterThan() {
        ZyStack result = Interpreter.interpret("[1 2 3 4 5] {2 >} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(3));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(4));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(5));
    }

    @Test
    public void shouldFilterWithEquality() {
        ZyStack result = Interpreter.interpret("[1 2 2 3 2] {2 =} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(2));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(2));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(2));
    }

    @Test
    public void shouldReturnEmptyListWhenNoMatch() {
        ZyStack result = Interpreter.interpret("[1 2 3] {10 =} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).isEmpty();
    }

    @Test
    public void shouldReturnAllElementsWhenAllMatch() {
        ZyStack result = Interpreter.interpret("[1 2 3] {0 >} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
    }

    @Test
    public void shouldFilterEvenNumbers() {
        ZyStack result = Interpreter.interpret("[1 2 3 4 5 6] {2 mod 0 =} filtrele");

        assertThat(result).hasSize(1);
        ZyList resultList = (ZyList) result.pop();
        assertThat(resultList.getValue()).hasSize(3);
        assertThat(resultList.getValue().get(0)).isEqualTo(new ZyNumber(2));
        assertThat(resultList.getValue().get(1)).isEqualTo(new ZyNumber(4));
        assertThat(resultList.getValue().get(2)).isEqualTo(new ZyNumber(6));
    }

    @Test
    public void shouldReturnEmptyListForEmptyInput() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(3));
        proc.add(new ZyOperator("<="));

        stack.push(inputList);
        stack.push(proc);

        filterFunction.process(stack);

        assertThat(stack).hasSize(1);
        ZyList result = (ZyList) stack.pop();
        assertThat(result.getValue()).isEmpty();
    }

    @Test
    public void shouldThrowWhenPredicateReturnsNonBoolean() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(1));
        ZyList inputList = new ZyList(listItems);

        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyNumber(2));
        proc.add(new ZyOperator("*"));

        stack.push(inputList);
        stack.push(proc);

        assertThatThrownBy(() -> filterFunction.process(stack))
                .isInstanceOf(ZyTypeError.class)
                .hasMessageContaining("doğruluk değeri döndürmeli");
    }

    @Test
    public void shouldThrowWhenProcedureIsNotProcedure() {
        ArrayList<ZyObject<?>> listItems = new ArrayList<>();
        listItems.add(new ZyNumber(1));
        ZyList inputList = new ZyList(listItems);

        stack.push(inputList);
        stack.push(new ZyNumber(42));

        assertThatThrownBy(() -> filterFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowWhenListIsNotList() {
        ZyProcedure proc = new ZyProcedure();
        proc.add(new ZyBoolean(true));

        stack.push(new ZyNumber(42));
        stack.push(proc);

        assertThatThrownBy(() -> filterFunction.process(stack))
                .isInstanceOf(ZyTypeError.class);
    }

    @Test
    public void shouldThrowWhenStackIsEmpty() {
        assertThatThrownBy(() -> filterFunction.process(stack))
                .isInstanceOf(ZyStackUnderflowError.class);
    }
}
