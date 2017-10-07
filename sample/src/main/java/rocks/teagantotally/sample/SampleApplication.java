package rocks.teagantotally.sample;

import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

import rocks.teagantotally.deepthought_routing.Route;
import rocks.teagantotally.deepthought_routing.Router;
import rocks.teagantotally.deepthought_routing.activities.RouteProcessingActivity;
import rocks.teagantotally.deepthought_routing.exceptions.MalformedRouteException;
import rocks.teagantotally.deepthought_routing.interfaces.RouteListener;
import rocks.teagantotally.sample.fragments.LoginFragment;
import rocks.teagantotally.sample.fragments.RegisterFragment;
import rocks.teagantotally.sample.fragments.UserLandingFragment;

/**
 * Created by tglenn on 10/7/17.
 */

public class SampleApplication
          extends Application
          implements RouteListener {
    public static final Uri ROUTES_URI = Uri.parse("link://sample/routes");
    private static final String TAG = "SampleApplication";

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate() {
        super.onCreate();
        Router router = Router.builder(this)
                              .addRoutedFragment(LoginFragment.class,
                                                 RegisterFragment.class,
                                                 UserLandingFragment.class)
                              .setRoutesUri(ROUTES_URI)
                              .setUserPermissionProvider(new PermissionProvider())
                              .build();
        RouteProcessingActivity.setRouter(router);
    }

    /**
     * Event handler for when a route is handled
     *
     * @param route  Route that was handled
     * @param extras Extras passed to the route
     */
    @Override
    public void onRouteHandled(Route route,
                               Bundle extras) {
        Log.i(TAG,
              "onRouteHandled: " + route.getUri());
    }

    /**
     * Event handler for when a route could not be found for the given uri
     *
     * @param requestedUri Requested uri
     */
    @Override
    public void onRouteNotFound(Uri requestedUri) {
        Log.i(TAG,
              "onRouteNotFound: " + requestedUri);
    }

    /**
     * Event handler for when a route can not be accessed due to lack of permissions
     *
     * @param requestedUri        Requested uri
     * @param requiredPermissions The required permissions
     */
    @Override
    public void onRouteNotAuthorized(Uri requestedUri,
                                     Set<String> requiredPermissions) {
        Log.i(TAG,
              "onRouteNotAuthorized: " + requestedUri);
    }

    /**
     * Event handler for when a requested route cannot be parsed due to missing or
     * inconvertable data types
     *
     * @param malformedRouteExceptions The malformed route errors that occurred
     */
    @Override
    public void onMalformedRoute(MalformedRouteException[] malformedRouteExceptions) {
        Log.i(TAG,
              "onMalformedRoute: " + malformedRouteExceptions);
    }
}
