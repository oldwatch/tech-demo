package org.demo.tools.idconvert;


import module java.base;
import module tools.jackson.databind;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface EntityIDMask {

}
