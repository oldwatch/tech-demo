//package org.demo.tools.idconvert.jackson;
//
//import module tools.jackson.databind;
//
//
//public class SealedClsJacksonModule extends SimpleModule {
//
//
//    @Override
//    public String getModuleName() {
//        return "Jdk17SealedClassesModule";
//    }
//
//    @Override
//    public Version version() {
//        return Version.unknownVersion();
//    }
//
//    @Override
//    public void setupModule(SetupContext context) {
//
//        // Add our sealed classes handler at the end of the handler list
//        context.appendAnnotationIntrospector(new SealedClsAnnotationIntrospector());
//    }
//
//}
