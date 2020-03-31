package provider;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface ResponseCache {
    String[] responseHeaderName() default "";

    boolean enabled() default true;

    int expireInMinutes() default 10;
}
