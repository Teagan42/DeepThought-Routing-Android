package rocks.teagantotally.deepthought_routing.annotations;

/**
 * Created by tglenn on 9/30/17.
 */

public @interface QueryParam {
    String value();

    String description() default "";

    ParameterType type();

    boolean required() default false;
}
