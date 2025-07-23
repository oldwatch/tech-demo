package com.demo.dubbo.service.impl;

import com.demo.dubbo.service.DemoService;
import com.demo.dubbo.service.entity.DemoEntity;
import com.demo.dubbo.service.entity.DemoInput;
import com.demo.dubbo.service.entity.Pager;
import org.apache.dubbo.config.annotation.DubboService;
import org.demo.idconvert.IdEncodeTool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@DubboService
public class DemoServiceImpl implements DemoService {

    private final AtomicInteger seq = new AtomicInteger(1);
    private final Map<String, DemoEntity> dataMap = new ConcurrentHashMap<>();
    private final IdEncodeTool tool;

    public DemoServiceImpl(IdEncodeTool tool) {
        this.tool = tool;
    }

    @Override
    public DemoEntity addData(DemoInput input) {
        String id = tool.encode(seq.incrementAndGet());
        var entity = new DemoEntity(input, id, "test");
        dataMap.put(entity.etityId(), entity);
        return entity;
    }

    @Override
    public List<DemoEntity> getDataListByPage(Pager pager) {
        return dataMap.values().stream().toList();
    }

    @Override
    public DemoEntity updateData(String id, DemoInput input) {
        var oldEntity = dataMap.get(id);
        var newRecord = new DemoEntity(input, oldEntity);
        dataMap.put(id, newRecord);
        return newRecord;
    }

    @Override
    public DemoEntity queryByID(String id) {
        return dataMap.get(id);
    }
}
