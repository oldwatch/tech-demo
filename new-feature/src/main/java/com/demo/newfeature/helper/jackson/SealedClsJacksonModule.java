package com.demo.newfeature.helper.jackson;


import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class SealedClsJacksonModule extends SimpleModule {


    @Override
    public String getModuleName() {
        return "Jdk17SealedClassesModule";
    }

    @Override
    public Version version() {
        // TODO Generate proper version?
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {

        // Add our sealed classes handler at the end of the handler list
        context.appendAnnotationIntrospector(new SealedClsAnnotationIntrospector());
    }

}
