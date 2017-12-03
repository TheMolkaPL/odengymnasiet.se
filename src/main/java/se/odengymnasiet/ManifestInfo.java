package se.odengymnasiet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ManifestInfo {

    String name();

    Class<? extends Manifest> parent();

    Class<? extends Controller> master();

    String route() default "";
}
