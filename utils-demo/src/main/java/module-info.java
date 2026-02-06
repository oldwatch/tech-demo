module demo.utils {
    requires org.slf4j;
    requires tools.jackson.databind;
    requires org.javamoney.moneta;
    requires spring.data.commons;
    requires spring.core;

    exports org.demo.utils;
    exports org.demo.money;
}