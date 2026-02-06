package org.demo.tools.idconvert.jackson;

import module tools.jackson.databind;
import org.demo.tools.idconvert.EntityIDMask;
import org.demo.tools.idconvert.IdEncodeTool;

public class EntityIdAnnotationIntrospector extends NopAnnotationIntrospector {


    private final EntityIdConvert convert;
    private final EntityIDMaskConvert maskConvert;

    public EntityIdAnnotationIntrospector(IdEncodeTool encodeTool) {
        this.convert = new EntityIdConvert(encodeTool);
        this.maskConvert = new EntityIDMaskConvert(encodeTool);
    }

    @Override
    public Object findSerializationConverter(MapperConfig<?> config, Annotated a) {
        var ann = _findAnnotation(a, EntityIDMask.class);
        return (ann == null) ? null : convert;
    }

    @Override
    public Object findDeserializationConverter(MapperConfig<?> config, Annotated a) {
        var ann = _findAnnotation(a, EntityIDMask.class);
        return (ann == null) ? null : maskConvert;
    }

}
