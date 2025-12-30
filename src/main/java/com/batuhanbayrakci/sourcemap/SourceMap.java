package com.batuhanbayrakci.sourcemap;

import com.batuhanbayrakci.objects.ZyObject;

import java.util.HashMap;
import java.util.Map;

public abstract class SourceMap {

    private static final int NO_LINE = -1;

    private static final Map<ZyObject, Integer> sourceMap = new HashMap<>();

    public static void addObject(ZyObject object, int line) {
        sourceMap.put(object, line);
    }

    public static Integer getLineOf(ZyObject object) {
        return sourceMap.getOrDefault(object, NO_LINE);
    }
}
