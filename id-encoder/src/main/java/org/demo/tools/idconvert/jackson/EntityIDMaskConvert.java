package org.demo.tools.idconvert.jackson;

import module tools.jackson.databind;
import org.demo.tools.idconvert.IdEncodeTool;


public class EntityIDMaskConvert extends StdConverter<String, Integer> {
    private final IdEncodeTool encodeTool;

    public EntityIDMaskConvert(IdEncodeTool encodeTool) {
        this.encodeTool = encodeTool;
    }

    @Override
    public Integer convert(String source) {
        return encodeTool.decode(source);
    }

}
