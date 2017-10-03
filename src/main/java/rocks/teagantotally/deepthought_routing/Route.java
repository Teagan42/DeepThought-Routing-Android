package rocks.teagantotally.deepthought_routing;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import rocks.teagantotally.deepthought_routing.annotations.ActivityRouteDefinition;
import rocks.teagantotally.deepthought_routing.annotations.FragmentRouteDefinition;
import rocks.teagantotally.deepthought_routing.annotations.ParameterType;
import rocks.teagantotally.deepthought_routing.annotations.QueryParam;
import rocks.teagantotally.deepthought_routing.annotations.UrlParam;
import timber.log.Timber;

/**
 * Created by tglenn on 9/29/17.
 */

public class Route {
    private static Function<UrlParam, String> urlParamKeyFunction =
              new Function<UrlParam, String>() {
                  @Nullable
                  @Override
                  public String apply(UrlParam input) {
                      return input.value();
                  }
              };
    private static Function<QueryParam, String> queryParamKeyFunction =
              new Function<QueryParam, String>() {
                  @Nullable
                  @Override
                  public String apply(QueryParam input) {
                      return input.value();
                  }
              };

    private Uri route;
    private String description;
    private Class<? extends AppCompatActivity> activityClass;
    private Class<? extends Fragment> fragmentClass;
    private Set<String> requiredPermissions = new HashSet<>();
    private Map<String, UrlParam> urlParameters = new HashMap<>();
    private Map<String, QueryParam> queryParameters = new HashMap<>();

    /**
     * Create a new route
     *
     * @param activityClass   Class of activity to launch
     * @param routeDefinition Route definition
     */
    Route(Class<? extends AppCompatActivity> activityClass,
          ActivityRouteDefinition routeDefinition) {
        this(routeDefinition.value(),
             activityClass,
             null,
             routeDefinition.description(),
             routeDefinition.requiredPermissions(),
             routeDefinition.urlParameters(),
             routeDefinition.queryParameters());
    }

    /**
     * Create a new route
     *
     * @param fragmentClass   Class of fragment to load
     * @param routeDefinition Route definition
     */
    Route(Class<? extends Fragment> fragmentClass,
          FragmentRouteDefinition routeDefinition) {
        this(routeDefinition.value(),
             routeDefinition.activity(),
             fragmentClass,
             routeDefinition.description(),
             routeDefinition.requiredPermissions(),
             routeDefinition.urlParameters(),
             routeDefinition.queryParameters());
    }

    Route(String route,
          Class<? extends AppCompatActivity> activityClass,
          Class<? extends Fragment> fragmentClass,
          String description,
          String[] requiredPermissions,
          UrlParam[] urlParameters,
          QueryParam[] queryParameters) {
        this.route = Uri.parse(route);
        this.activityClass = activityClass;
        this.fragmentClass = fragmentClass;
        this.description = description;
        if (urlParameters != null && urlParameters.length > 0) {
            this.urlParameters.putAll(
                      Maps.uniqueIndex(Arrays.asList(urlParameters),
                                       urlParamKeyFunction));
        }
        if (queryParameters != null && queryParameters.length > 0) {
            this.queryParameters.putAll(
                      Maps.uniqueIndex(Arrays.asList(queryParameters),
                                       queryParamKeyFunction));
        }
        if (requiredPermissions != null && requiredPermissions.length > 0) {
            this.requiredPermissions.addAll(Arrays.asList(requiredPermissions));
        }
    }

    /**
     * Tries to match the requested
     *
     * @param requestedUri The uri requested
     * @return A bundle of url and query parameters if matched, null otherwise
     */
    Bundle matchUri(Uri requestedUri) {
        if (TextUtils.isEmpty(route.toString()) || TextUtils.isEmpty(requestedUri.toString())) {
            Timber.d("Empty routes");
            return null;
        }

        if (Objects.equals(route,
                           requestedUri)) {
            Timber.d("Route is an exact match");
            return new Bundle();
        }

        if (!TextUtils.equals(requestedUri.getScheme(),
                              route.getScheme())) {
            Timber.d("Requested uri %s does not match scheme %s",
                     requestedUri.toString(),
                     route.getScheme());
            return null;
        }

        if (!TextUtils.equals(requestedUri.getAuthority(),
                              route.getAuthority())) {
            Timber.d("Requested uri %s does not match authority %s",
                     requestedUri.toString(),
                     route.getAuthority());
            return null;
        }

        List<String> requestedSegments = requestedUri.getPathSegments();
        List<String> compareSegments = route.getPathSegments();

        Bundle extras = new Bundle();
        for (int i = 0; i < compareSegments.size(); i++) {
            String compareSegment = compareSegments.get(i);

            if (compareSegment.startsWith(":")) {
                String param = compareSegment.substring(1);
                UrlParam paramSpec = urlParameters.get(param);
                if (paramSpec.required() && requestedSegments.size() <= i) {
                    Timber.d("Requested uri %s is missing required url parameter %s",
                             requestedUri.toString(),
                             param);
                    return null;
                }

                String requestedSegment = requestedSegments.get(i);
                addParameterToExtras(param,
                                     requestedSegment,
                                     urlParameters.get(param)
                                                  .type(),
                                     extras);
            } else {
                String requestedSegment =
                          requestedSegments.size() > i
                          ? requestedSegments.get(i)
                          : null;
                if (!TextUtils.equals(requestedSegment,
                                      compareSegment)) {
                    Timber.d("Requested uri %s is missing path segment %s at position %d",
                             requestedUri.toString(),
                             compareSegment,
                             i);
                    return null;
                }
            }
        }

        for (QueryParam queryParam : queryParameters.values()) {
            String queryValue = requestedUri.getQueryParameter(queryParam.value());
            if (queryParam.required() && queryValue == null) {
                Timber.d("Requested uri %s is missing required query parameter %s",
                         requestedUri.toString(),
                         queryParam.value());
                return null;
            }

            addParameterToExtras(queryParam.value(),
                                 queryValue,
                                 queryParam.type(),
                                 extras);
        }

        return extras;
    }

    private void addParameterToExtras(String parameter,
                                      String value,
                                      ParameterType type,
                                      Bundle extras) {
        switch (type) {
            case STRING:
                extras.putString(parameter,
                                 value);
                break;
            case SHORT:
                extras.putShort(parameter,
                                Short.valueOf(value));
                break;
            case INTEGER:
                extras.putInt(parameter,
                              Integer.valueOf(value));
                break;
            case LONG:
                extras.putLong(parameter,
                               Long.valueOf(value));
                break;
            case FLOAT:
                extras.putFloat(parameter,
                                Float.valueOf(value));
                break;
            case DOUBLE:
                extras.putDouble(parameter,
                                 Double.valueOf(value));
                break;
            case BOOLEAN:
                extras.putBoolean(parameter,
                                  Boolean.valueOf(value));
                break;
            case BYTE:
                extras.putByte(parameter,
                               Byte.valueOf(value));
                break;
        }
    }

    /**
     * @return The route uri
     */
    public Uri getUri() {
        return route;
    }

    /**
     * @return The activity class
     */
    public Class<? extends AppCompatActivity> getActivityClass() {
        return activityClass;
    }

    /**
     * @return The fragment class
     */
    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    /**
     * @return The set of permissions required to access this route
     */
    public Set<String> getRequiredPermissions() {
        return requiredPermissions;
    }

    /**
     * @return The description for this route
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The url parameters for this route
     */
    public Collection<UrlParam> getUrlParameters() {
        return urlParameters.values();
    }

    /**
     * @return The query parameters for this route
     */
    public Collection<QueryParam> getQueryParameters() {
        return queryParameters.values();
    }
}
