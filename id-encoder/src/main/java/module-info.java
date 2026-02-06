module id.encoder {

    requires demo.utils;
    requires org.slf4j;
    requires tools.jackson.databind;
    requires org.javamoney.moneta;
    requires sqids;
    requires spring.boot.jackson;
    requires spring.boot;
    requires spring.context;

    exports org.demo.tools.idconvert;

}