package org.demo.idconvert.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.demo.idconvert.IdEncodeTool;

import java.io.IOException;

//@Component
public class EntityIdJsonSerializer extends StdSerializer<Integer> {


    private final IdEncodeTool encodeTool;

    public EntityIdJsonSerializer(IdEncodeTool encodeTool) {
        super(Integer.class);

        this.encodeTool = encodeTool;
    }

    @Override
    public void serialize(Integer val, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(encodeTool.encode(val));
    }

    @Override
    protected boolean isDefaultSerializer(JsonSerializer<?> serializer) {
        return false;
    }

//    @Override
//    public JsonSerializer<?> findContextualConvertingSerializer(SerializerProvider prov, BeanProperty property) {
//        if (property != null) {
//            var annotation = property.getAnnotation(EntityIDMask.class);
//            if (annotation != null) {
//                return this;
//            }
//        }
//        return this;
//    }

    @Override
    protected JsonSerializer<?> findAnnotatedContentSerializer(SerializerProvider serializers,
                                                               BeanProperty property)
            throws JsonMappingException {
        if (property != null) {
            // First: if we have a property, may have property-annotation overrides
            AnnotatedMember m = property.getMember();
            final AnnotationIntrospector intr = serializers.getAnnotationIntrospector();
            if (m != null) {
                Object serDef = intr.findContentSerializer(m);
                if (serDef != null) {
                    return serializers.serializerInstance(m, serDef);
                }
            }
        }
        return null;
    }

}
