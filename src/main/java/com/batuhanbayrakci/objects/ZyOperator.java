package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;
import com.batuhanbayrakci.exception.ZyError;
import com.batuhanbayrakci.modules.operatorfunctions.DivideOperator;
import com.batuhanbayrakci.modules.operatorfunctions.EqualOperator;
import com.batuhanbayrakci.modules.operatorfunctions.GreaterThanOperator;
import com.batuhanbayrakci.modules.operatorfunctions.GreaterThanOrEqualOperator;
import com.batuhanbayrakci.modules.operatorfunctions.IncrementOperator;
import com.batuhanbayrakci.modules.operatorfunctions.LessThanOperator;
import com.batuhanbayrakci.modules.operatorfunctions.LessThanOrEqualOperator;
import com.batuhanbayrakci.modules.operatorfunctions.MinusOperator;
import com.batuhanbayrakci.modules.operatorfunctions.MultiplyOperator;
import com.batuhanbayrakci.modules.operatorfunctions.NotEqualOperator;
import com.batuhanbayrakci.modules.operatorfunctions.PlusOperator;
import com.batuhanbayrakci.modules.operatorfunctions.ZyOperatorFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ZyOperator extends ZyObject<String> {

    private final Map<String, ZyOperatorFunction> systemOperatorFunctions = new HashMap<>();

    public ZyOperator(String value) {
        super(value);
        loadSystemOperatorFunctions();
    }

    private void loadSystemOperatorFunctions() {
        systemOperatorFunctions.put("+", new PlusOperator());
        systemOperatorFunctions.put("-", new MinusOperator());
        systemOperatorFunctions.put("*", new MultiplyOperator());
        systemOperatorFunctions.put("/", new DivideOperator());
        systemOperatorFunctions.put("++", new IncrementOperator());
        systemOperatorFunctions.put("=", new EqualOperator());
        systemOperatorFunctions.put("=!", new NotEqualOperator());
        systemOperatorFunctions.put("<", new LessThanOperator());
        systemOperatorFunctions.put("<=", new LessThanOrEqualOperator());
        systemOperatorFunctions.put(">", new GreaterThanOperator());
        systemOperatorFunctions.put(">=", new GreaterThanOrEqualOperator());
    }

    public Optional<ZyOperatorFunction> findOperatorFunction(String name) {
        return Optional.ofNullable(systemOperatorFunctions.get(name));
    }

    @Override
    public String getType() {
        return "operator";
    }

    @Override
    public void process(ZyStack stack) throws ZyError {
        execute(stack);
    }

    @Override
    public void execute(ZyStack stack) throws ZyError {
        Optional<ZyOperatorFunction> operatorFunction = findOperatorFunction(getValue());
        if (operatorFunction.isPresent()) {
            operatorFunction.get().process(stack);
        } else {
            throw new ZyError("No such operator '" + getValue() + "'");
        }
    }
}
