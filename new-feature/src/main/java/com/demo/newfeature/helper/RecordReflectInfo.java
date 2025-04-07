package com.demo.newfeature.helper;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public record RecordReflectInfo(Constructor<?> constructor, Map<String, RecordComponent> methodMap) {

    RecordReflectInfo(Class<? extends Record> recCls) {
        Map<String, RecordComponent> accessFunsMap = Arrays.stream(recCls.getRecordComponents())
                .reduce(new HashMap<String, RecordComponent>(),
                        (m, e) -> {
                            m.put(e.getName(), e);
                            return m;
                        },
                        (m1, m2) -> {
                            m1.putAll(m2);
                            return m1;
                        });

        var clsArray = Arrays.stream(recCls.getRecordComponents())
                .map(RecordComponent::getType).toArray(Class[]::new);

        Constructor<?> constructFun = null;
        try {
            constructFun = recCls.getConstructor(clsArray);
        } catch (NoSuchMethodException e) {
            constructFun = recCls.getConstructors()[0];
        }

        this(constructFun, accessFunsMap);
    }


    Object invokeAccess(String fieldName, Record rec) {
        RecordComponent component = methodMap.get(fieldName);
        if (component == null) {
            return null;
        }
        try {
            return component.getAccessor().invoke(rec);
        } catch (ReflectiveOperationException e) {
            log.warn("reflect fail:field: {}", fieldName);
            return null;
        }
    }

    Object invokeAccess(String fieldName, Record rec, Class<?> targetType) {

        RecordComponent component = methodMap.get(fieldName);
        if (!component.getType().equals(targetType)) {
            return null;
        }
        return invokeAccess(fieldName, rec);
    }

    Object construct(Object[] paramArray) {
        try {
            return constructor.newInstance(paramArray);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
