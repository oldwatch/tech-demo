package com.demo.kafka.tools.entity;

import java.util.ArrayList;
import java.util.List;

public class EntityList {
    private List<DataEntity> list = new ArrayList<>();

    public EntityList() {

    }

    public EntityList(DataEntity entity) {
        list.add(entity);
    }

    public void addEntity(DataEntity entity) {
        this.list.add(entity);
    }

    public List<DataEntity> getList() {
        return list;
    }

    public void setList(List<DataEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "EntityList{" +
                "list=" + list +
                '}';
    }
}
