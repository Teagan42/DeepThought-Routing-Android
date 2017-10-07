package rocks.teagantotally.deepthought_routing.annotations;

import android.support.v7.app.AppCompatActivity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import rocks.teagantotally.deepthought_routing.RoutedActivity;

/**
 * Created by tglenn on 9/29/17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FragmentRouteDefinition {
    String value();

    String description() default "";

    String[] requiredPermissions() default {};

    Class<? extends AppCompatActivity> activity() default RoutedActivity.class;

    UrlParam[] urlParameters() default {};

    QueryParam[] queryParameters() default {};
}
