package com.demo.kafka.tools.entity;

import java.util.HashSet;
import java.util.Set;

public class SummaryEntity {
    private final Set<String> names = new HashSet<>();
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

    public Set<String> getNames() {
        return names;
    }

    public int getSum() {
        return sum;
    }

    public int getCount() {
        return count;
    }

}
