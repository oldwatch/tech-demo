package org.demo.helper.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.demo.helper.IdEncodeTool;

public class EntityIDMaskConvert implements Converter<String, Integer> {
    private final IdEncodeTool encodeTool;

    public EntityIDMaskConvert(IdEncodeTool encodeTool) {
        this.encodeTool = encodeTool;
    }

    @Override
    public Integer convert(String source) {
        return encodeTool.decode(source);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.class);
    }
}
