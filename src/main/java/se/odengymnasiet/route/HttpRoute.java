package se.odengymnasiet.route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Simple description of a route which can handle HTTP requests.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpRoute {

    String value();

    RequestMethod[] methods() default RequestMethod.GET;
}
