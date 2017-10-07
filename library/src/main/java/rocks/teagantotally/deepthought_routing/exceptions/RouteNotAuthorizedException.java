package rocks.teagantotally.deepthought_routing.exceptions;

import android.net.Uri;

import rocks.teagantotally.deepthought_routing.Route;

/**
 * Created by tglenn on 10/2/17.
 */

public class RouteNotAuthorizedException
          extends RoutingException {
    private Route route;

    /**
     * Instantiate a RoutingException object
     *
     * @param requestedUri The requested uri
     * @param route        The route that was matched
     */
    public RouteNotAuthorizedException(Uri requestedUri,
                                       Route route) {
        super(requestedUri);
        this.route = route;
    }

    /**
     * @return The route that was matched
     */
    public Route getRoute() {
        return route;
    }
}
