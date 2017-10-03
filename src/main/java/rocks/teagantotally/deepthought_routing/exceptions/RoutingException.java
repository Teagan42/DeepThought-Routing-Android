package rocks.teagantotally.deepthought_routing.exceptions;

import android.net.Uri;

/**
 * Created by tglenn on 10/2/17.
 */

public abstract class RoutingException
          extends Exception {
    private Uri requestedUri;

    /**
     * Instantiate a RoutingException object
     * @param requestedUri The requested uri
     */
    public RoutingException(Uri requestedUri) {
        this.requestedUri = requestedUri;
    }

    /**
     * @return The requested uri
     */
    public Uri getRequestedUri() {
        return requestedUri;
    }
}
