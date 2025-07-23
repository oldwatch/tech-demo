package org.demo.idconvert.jackson;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.demo.idconvert.IdEncodeTool;

public class EntityIdConvert implements Converter<Integer, String> {

    private final IdEncodeTool encodeTool;

    public EntityIdConvert(IdEncodeTool encodeTool) {
        this.encodeTool = encodeTool;
    }

    @Override
    public String convert(Integer source) {
        return encodeTool.encode(source);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Integer.TYPE);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }
}
