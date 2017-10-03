package rocks.teagantotally.deepthought_routing.interfaces;

import android.net.Uri;
import android.os.Bundle;

import java.util.Set;

import rocks.teagantotally.deepthought_routing.Route;
import rocks.teagantotally.deepthought_routing.exceptions.MalformedRouteException;

/**
 * Created by tglenn on 9/30/17.
 */

public interface RouteListener {
    /**
     * Event handler for when a route is handled
     *
     * @param route  Route that was handled
     * @param extras Extras passed to the route
     */
    void onRouteHandled(Route route,
                        Bundle extras);

    /**
     * Event handler for when a route could not be found for the given uri
     *
     * @param requestedUri Requested uri
     */
    void onRouteNotFound(Uri requestedUri);

    /**
     * Event handler for when a route can not be accessed due to lack of permissions
     *
     * @param requestedUri        Requested uri
     * @param requiredPermissions The required permissions
     */
    void onRouteNotAuthorized(Uri requestedUri,
                              Set<String> requiredPermissions);

    /**
     * Event handler for when a requested route cannot be parsed due to missing or
     * inconvertable data types
     *
     * @param malformedRouteExceptions The malformed route errors that occurred
     */
    void onMalformedRoute(MalformedRouteException[] malformedRouteExceptions);
}
