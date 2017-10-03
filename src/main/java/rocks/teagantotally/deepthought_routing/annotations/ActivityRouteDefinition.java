package rocks.teagantotally.deepthought_routing.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tglenn on 9/29/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActivityRouteDefinition {
    String value();

    String[] requiredPermissions() default {};

    String description() default "";

    UrlParam[] urlParameters() default {};

    QueryParam[] queryParameters() default {};
}
