package org.demo.tools.idconvert.jackson;

import module tools.jackson.databind;
import org.demo.tools.idconvert.IdEncodeTool;


public class EntityIdConvert extends StdConverter<Integer, String> {

    private final IdEncodeTool encodeTool;

    public EntityIdConvert(IdEncodeTool encodeTool) {
        this.encodeTool = encodeTool;
    }

    @Override
    public String convert(Integer value) {
        return encodeTool.encode(value);
    }
}
