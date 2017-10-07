package rocks.teagantotally.deepthought_routing.exceptions;

import android.net.Uri;

/**
 * Created by tglenn on 10/2/17.
 */

public class RouteNotFoundException
          extends RoutingException {
    /**
     * Instantiate a RoutingException object
     *
     * @param requestedUri The requested uri
     */
    public RouteNotFoundException(Uri requestedUri) {
        super(requestedUri);
    }
}
