package rocks.teagantotally.deepthought_routing.activities.viewmodels;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import java.util.Objects;

import rocks.teagantotally.deepthought_routing.annotations.ParameterType;
import rocks.teagantotally.deepthought_routing.annotations.QueryParam;
import rocks.teagantotally.deepthought_routing.annotations.UrlParam;

/**
 * Created by tglenn on 10/2/17.
 */

public class ParameterViewModel
          extends BaseObservable {
    public enum RequestLocation {
        URL,
        QUERY
    }

    private String name;
    private String description;
    private ParameterType type;
    private boolean required;
    private boolean expanded = true;
    private RequestLocation requestLocation;

    public ParameterViewModel(@NonNull QueryParam queryParam) {
        Objects.requireNonNull(queryParam,
                               "Parameter annotation cannot be null");
        this.name = queryParam.value();
        this.description = queryParam.description();
        this.type = queryParam.type();
        this.required = queryParam.required();
        this.requestLocation = RequestLocation.QUERY;
    }

    public ParameterViewModel(@NonNull UrlParam urlParam) {
        Objects.requireNonNull(urlParam,
                               "Parameter annotation cannot be null");
        this.name = urlParam.value();
        this.description = urlParam.description();
        this.type = urlParam.type();
        this.required = true;
        this.requestLocation = RequestLocation.URL;
    }

    /**
     * @return The parameter name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The description for this paramter
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The visibility for the collapsed uri view
     */
    public int getCollapsedUriVisibility() {
        return !expanded
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility of the details when expanded
     */
    public int getExpandedDetailVisibility() {
        return expanded
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility of the description view
     */
    public int getDescriptionVisibility() {
        return !TextUtils.isEmpty(description) && expanded
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility for the required view
     */
    public int getRequiredVisibility() {
        return required && expanded
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The name for this parameter's type
     */
    public String getTypeName() {
        return type.getTypeName();
    }

    /**
     * @return The location in the request for this parameter
     */
    public String getRequestLocation() {
        return requestLocation.name();
    }
}
