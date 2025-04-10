package com.demo.newfeature.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecordUtils {

    private final static Logger log = LoggerFactory.getLogger(RecordUtils.class);

    private final static Map<Class<? extends Record>, RecordReflectInfo> recInfoMap = new ConcurrentHashMap<>();


    private static RecordReflectInfo getRecInfo(Class<? extends Record> recCls) {
        return recInfoMap.computeIfAbsent(recCls, RecordReflectInfo::new);
    }

    public static <R extends Record> R duplicate(R old) {
        return duplicate(old, Map.of());
    }


    @SuppressWarnings("unchecked")
    public static <R extends Record> R duplicate(R old, Map<String, Object> params) {

        var cls = old.getClass();
        if (!cls.isRecord()) {
            throw new IllegalArgumentException();
        }
        RecordReflectInfo recInfo = getRecInfo(cls);

        var paramArray = Arrays.stream(cls.getRecordComponents())
                .map(comp -> {
                    String field = comp.getName();
                    return params.getOrDefault(field, recInfo.invokeAccess(field, old));
                }).toArray(Object[]::new);
        return (R) recInfo.construct(paramArray);

    }

    public static <S extends Record, T extends Record> T copy(S source, Class<T> targetClass) {
        return copy(source, targetClass, Map.of());
    }

    @SuppressWarnings("unchecked")
    public static <S extends Record, T extends Record> T copy(S source, Class<T> targetCls, Map<String, Object> extValues) {

        assert extValues != null;
        var srcCls = source.getClass();
        if (!srcCls.isRecord() || !targetCls.isRecord()) {
            throw new IllegalArgumentException();
        }

        RecordReflectInfo srcInfo = getRecInfo(srcCls);
        RecordReflectInfo targetInfo = getRecInfo(targetCls);

        var paramArray = Arrays.stream(targetCls.getRecordComponents())
                .map(comp -> {
                    String field = comp.getName();
                    return extValues.getOrDefault(field, srcInfo.invokeAccess(field, source, comp.getType()));
                }).toArray(Object[]::new);
        return (T) targetInfo.construct(paramArray);


    }


}
