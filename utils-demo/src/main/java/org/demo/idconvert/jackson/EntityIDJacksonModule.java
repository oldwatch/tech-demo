package org.demo.idconvert.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.demo.idconvert.IdEncodeTool;

//@Component
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
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        // Add our sealed classes handler at the end of the handler list
        context.appendAnnotationIntrospector(new EntityIdAnnotationIntrospector(idEncodeTool));
    }

}
