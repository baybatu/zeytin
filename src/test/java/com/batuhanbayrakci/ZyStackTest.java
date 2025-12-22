package com.batuhanbayrakci;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZyStackTest {

    @Test
    public void it_should_Name() {
        Map<List<String>, Integer> m = new HashMap<>();

        ArrayList<String> objects = new ArrayList<>();
        objects.add("batuhan");

        m.put(objects, 1);

        assert m.get(objects) == 1;
    }
}