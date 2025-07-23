package com.demo.dubbo.service;

import com.demo.dubbo.service.entity.DemoEntity;
import com.demo.dubbo.service.entity.DemoInput;
import com.demo.dubbo.service.entity.Pager;

import java.util.List;

public interface DemoService {

    DemoEntity addData(DemoInput input);

    List<DemoEntity> getDataListByPage(Pager pager);

    DemoEntity updateData(String id, DemoInput input);

    DemoEntity queryByID(String id);

}
