package com.demo.dubbo.service;

import com.demo.dubbo.service.entity.DemoEntity;
import com.demo.dubbo.service.entity.DemoInput;
import com.demo.dubbo.service.entity.Pager;
import org.apache.dubbo.remoting.http12.HttpMethods;
import org.apache.dubbo.remoting.http12.rest.Mapping;
import org.apache.dubbo.remoting.http12.rest.Param;
import org.apache.dubbo.remoting.http12.rest.ParamType;

import java.util.List;

public interface DemoService {

    @Mapping(enabled = false)
    DemoEntity addData(DemoInput input) throws DemoServiceException;


    @Mapping(path = "/all", method = HttpMethods.GET)
    List<DemoEntity> getDataListByPage(Pager pager) throws DemoServiceException;

    @Mapping(enabled = false)
    DemoEntity updateData(String id, DemoInput input) throws DemoServiceException;

    @Mapping(path = "/id/{id}", method = HttpMethods.GET)
    DemoEntity queryByID(@Param(type = ParamType.PathVariable) String id) throws DemoServiceException;

}
