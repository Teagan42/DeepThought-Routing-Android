package rocks.teagantotally.deepthought_routing.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tglenn on 9/30/17.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface UrlParam {
    String value();

    String description() default "";

    boolean required() default true;

    ParameterType type() default ParameterType.STRING;
}
