package com.batuhanbayrakci.objects;

import com.batuhanbayrakci.ZyStack;

public class ZyEmpty extends ZyObject<String> {

    public ZyEmpty() {
        super("");
    }

    @Override
    public String getType() {
        return "bos";
    }

    @Override
    public void process(ZyStack stack) {
        // do nothing!
    }

}
