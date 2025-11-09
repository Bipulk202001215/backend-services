package com.example.common.id;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratedId {

    /**
     * Prefix to prepend to the generated identifier.
     */
    String prefix() default "";

    /**
     * Separator inserted between the prefix and the generated body when the prefix is not blank.
     */
    String separator() default "-";

    /**
     * Number of numeric characters to generate for the identifier body.
     */
    int length() default 12;
}

