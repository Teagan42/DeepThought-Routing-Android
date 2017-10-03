package rocks.teagantotally.deepthought_routing.exceptions;

import android.net.Uri;

import rocks.teagantotally.deepthought_routing.annotations.QueryParam;
import rocks.teagantotally.deepthought_routing.annotations.UrlParam;

/**
 * Created by tglenn on 10/2/17.
 */

public class MalformedRouteException
          extends RoutingException {
    public enum MalformationType {
        INCONVERTABLE,
        MISSING;
    }

    private UrlParam urlParam;
    private QueryParam queryParam;
    private MalformationType malformationType;

    /**
     * Instantiate a new malformed route exception
     *
     * @param requestedUri     The requested uri
     * @param urlParam         The url parameter that was malformed
     * @param malformationType The type of malformation
     */
    public MalformedRouteException(Uri requestedUri,
                                   UrlParam urlParam,
                                   MalformationType malformationType) {
        super(requestedUri);
        this.urlParam = urlParam;
        this.malformationType = malformationType;
    }

    /**
     * Instantiate a new malformed route exception
     *
     * @param requestedUri     The requested uri
     * @param queryParam       The query parameter that was malformed
     * @param malformationType The type of malformation
     */
    public MalformedRouteException(Uri requestedUri,
                                   QueryParam queryParam,
                                   MalformationType malformationType) {
        super(requestedUri);
        this.queryParam = queryParam;
        this.malformationType = malformationType;
    }

    /**
     * @return The name of the parameter that is malformed
     */
    public String getParameterName() {
        return this.queryParam == null
               ? this.urlParam.value()
               : this.queryParam.value();
    }

    /**
     * @return The type of parameter
     */
    public String getParameterType() {
        return this.queryParam == null
               ? this.urlParam.getClass()
                              .getSimpleName()
               : this.queryParam.getClass()
                                .getSimpleName();
    }

    /**
     * @return The type of malformation
     */
    public String getMalformation() {
        return this.malformationType.name();
    }
}
