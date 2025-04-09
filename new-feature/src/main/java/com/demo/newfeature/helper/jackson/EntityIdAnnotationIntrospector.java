package com.demo.newfeature.helper.jackson;

import com.demo.newfeature.helper.IdEncodeTool;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

public class EntityIdAnnotationIntrospector extends NopAnnotationIntrospector {


    private final EntityIdConvert convert;
    private final EntityIDMaskConvert maskConvert;

    public EntityIdAnnotationIntrospector(IdEncodeTool encodeTool) {
        this.convert = new EntityIdConvert(encodeTool);
        this.maskConvert = new EntityIDMaskConvert(encodeTool);
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }


    @Override
    public Object findSerializationConverter(Annotated a) {
        var ann = _findAnnotation(a, EntityIDMask.class);
        return (ann == null) ? null : convert;
    }

    @Override
    public Object findDeserializationConverter(Annotated a) {
        var ann = _findAnnotation(a, EntityIDMask.class);
        return (ann == null) ? null : maskConvert;
    }

}
