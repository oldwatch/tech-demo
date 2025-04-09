package com.demo.newfeature.helper.jackson;

import com.demo.newfeature.helper.IdEncodeTool;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class EntityIDJacksonModule extends SimpleModule {

    private final IdEncodeTool idEncodeTool;

    public EntityIDJacksonModule(IdEncodeTool idEncodeTool) {
        this.idEncodeTool = idEncodeTool;
    }

    @Override
    public String getModuleName() {
        return "EntityIDMaskModule";
    }

    @Override
    public Version version() {
        // TODO Generate proper version?
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        // Add our sealed classes handler at the end of the handler list
        context.appendAnnotationIntrospector(new EntityIdAnnotationIntrospector(idEncodeTool));
    }

}
