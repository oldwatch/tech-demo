//package org.demo.tools.idconvert.jackson;
//
//import module java.base;
//import module tools.jackson.databind;
//
//
//public class SealedClsAnnotationIntrospector extends NopAnnotationIntrospector {
//    @Override
//    public Version version() {
//        return Version.unknownVersion();
//    }
//
//
//    @Override
//    public List<NamedType> findSubtypes(Annotated a) {
//        if (a.getAnnotated() instanceof Class<?> klass && klass.isSealed()) {
//            Class<?>[] permittedSubclasses = klass.getPermittedSubclasses();
//            if (permittedSubclasses.length > 0) {
//                return Arrays.stream(permittedSubclasses).map(NamedType::new).toList();
//            }
//        }
//        return null;
//    }
//}
