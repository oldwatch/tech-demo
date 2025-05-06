package com.demo.kafka.tools.entity;

import java.util.ArrayList;
import java.util.List;

public class SummaryEntity {
    private final List<String> names = new ArrayList<>();
    private int sum;
    private int count;

    public SummaryEntity() {

    }

    public SummaryEntity add(String name, Integer val) {
        names.add(name);
        sum += val;
        count++;
        return this;
    }

    public List<String> getNames() {
        return names;
    }

    public int getSum() {
        return sum;
    }
    
    public int getCount() {
        return count;
    }

}
