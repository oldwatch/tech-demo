package org.demo.helper.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import java.util.Arrays;
import java.util.List;

public class SealedClsAnnotationIntrospector extends NopAnnotationIntrospector {
    @Override
    public Version version() {
        return Version.unknownVersion();
    }


    @Override
    public List<NamedType> findSubtypes(Annotated a) {
        if (a.getAnnotated() instanceof Class<?> klass && klass.isSealed()) {
            Class<?>[] permittedSubclasses = klass.getPermittedSubclasses();
            if (permittedSubclasses.length > 0) {
                return Arrays.stream(permittedSubclasses).map(NamedType::new).toList();
            }
        }
        return null;
    }
}
