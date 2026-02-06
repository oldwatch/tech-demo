//package org.demo.tools.idconvert.jackson;
//
//import module java.base;
//import module tools.jackson.databind;
//import org.demo.tools.idconvert.IdEncodeTool;
//
//public class EntityIdJsonSerializer extends StdSerializer<Integer> {
//
//
//    private final IdEncodeTool encodeTool;
//
//    public EntityIdJsonSerializer(IdEncodeTool encodeTool) {
//        super(Integer.class);
//
//        this.encodeTool = encodeTool;
//    }
//
//    @Override
//    public void serialize(Integer val, JsonGenerator jsonGenerator, SerializationContext serializerProvider) {
//
//        jsonGenerator.writeString(encodeTool.encode(val));
//    }
//
//}
