package rocks.teagantotally.deepthought_routing.activities.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import rocks.teagantotally.deepthought_routing.BR;
import rocks.teagantotally.deepthought_routing.R;
import rocks.teagantotally.deepthought_routing.Route;
import rocks.teagantotally.deepthought_routing.annotations.QueryParam;
import rocks.teagantotally.deepthought_routing.annotations.UrlParam;
import rocks.teagantotally.deepthoughtrecycler.binders.AbstractItemBinder;
import rocks.teagantotally.deepthoughtrecycler.binders.ItemBinder;

/**
 * Created by tglenn on 10/2/17.
 */

public class RouteViewModel
          extends BaseObservable {
    private static final int LONG_PERMISSION_NAME_LENGTH = 20;
    private static final boolean ENABLE_COLLAPSING = false;

    private static Predicate<String> longPermissionNamePredicate =
              new Predicate<String>() {
                  @Override
                  public boolean apply(String input) {
                      return input != null
                             && input.length() > LONG_PERMISSION_NAME_LENGTH;
                  }
              };
    private static AbstractItemBinder<String> permissionItemBinder =
              new ItemBinder<>(BR.permission,
                               R.layout.item_permission);
    private static AbstractItemBinder<ParameterViewModel> parameterItemBinder =
              new ItemBinder<>(BR.param,
                               R.layout.item_parameter);

    private Route route;
    private List<ParameterViewModel> urlParameters = new ArrayList<>();
    private List<ParameterViewModel> queryParameters = new ArrayList<>();
    private RecyclerView.LayoutManager permissionsLayoutManager;
    private RecyclerView.LayoutManager parametersLayoutManager;
    private boolean expanded = true;

    /**
     * Create a new view model for a route
     *
     * @param route The route object
     */
    public RouteViewModel(@NonNull Context context,
                          @NonNull Route route) {
        Objects.requireNonNull(context,
                               "Context cannot be null");
        Objects.requireNonNull(route,
                               "Route cannot be null");
        this.route = route;

        for (QueryParam queryParam : route.getQueryParameters()) {
            queryParameters.add(new ParameterViewModel(queryParam));
        }

        for (UrlParam urlParam : route.getUrlParameters()) {
            urlParameters.add(new ParameterViewModel(urlParam));
        }

        boolean hasLongPermissionName =
                  !Sets.filter(route.getRequiredPermissions(),
                               longPermissionNamePredicate)
                       .isEmpty();

        permissionsLayoutManager =
                  hasLongPermissionName || route.getRequiredPermissions()
                                                .size() < 2
                  ? new LinearLayoutManager(context)
                  : new GridLayoutManager(context,
                                          2);
        parametersLayoutManager =
                  new LinearLayoutManager(context,
                                          LinearLayoutManager.VERTICAL,
                                          false);
    }

    /**
     * Toggle the expanded view
     */
    public void toggleExpanded() {
        if (!ENABLE_COLLAPSING) {
            return;
        }
        this.expanded = !this.expanded;
        notifyChange();
    }

    /**
     * @return The layout manager for permissions
     */
    public RecyclerView.LayoutManager getPermissionsLayoutManager() {
        return permissionsLayoutManager;
    }

    /**
     * @return The item binder for permissions
     */
    public AbstractItemBinder<String> getPermissionItemBinder() {
        return permissionItemBinder;
    }

    /**
     * @return The layout manager for parameters
     */
    public RecyclerView.LayoutManager getParametersLayoutManager() {
        return parametersLayoutManager;
    }

    /**
     * @return The item binder for parameters
     */
    public AbstractItemBinder<ParameterViewModel> getParameterItemBinder() {
        return parameterItemBinder;
    }

    /**
     * @return The route uri
     */
    public String getUri() {
        return route.getUri()
                    .toString();
    }

    /**
     * @return The activity/fragment class path
     */
    public String getClassPath() {
        String path = route.getActivityClass()
                           .getSimpleName();
        if (route.getFragmentClass() != null) {
            path += " > ";
            path += route.getFragmentClass()
                         .getSimpleName();
        }

        return path;
    }

    /**
     * @return The description of the route
     */
    public String getDescription() {
        return route.getDescription();
    }

    /**
     * @return The required permissions for the route
     */
    public Collection<String> getRequiredPermissions() {
        return route.getRequiredPermissions();
    }

    /**
     * @return The parameters for the route
     */
    public Collection<ParameterViewModel> getParameters() {
        Collection<ParameterViewModel> parameters = new LinkedList<>();
        parameters.addAll(urlParameters);
        parameters.addAll(queryParameters);

        return parameters;
    }

    /**
     * @return The visibility for the description view
     */
    public int getDescriptionVisibility() {
        return !TextUtils.isEmpty(route.getDescription())
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility for the query parameters view
     */
    public int getParametersVisibility() {
        return !getParameters().isEmpty()
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility for the permissions view
     */
    public int getPermissionsVisibility() {
        return !route.getRequiredPermissions()
                     .isEmpty()
               ? View.VISIBLE
               : View.GONE;
    }

    /**
     * @return The visibility for the expanded details
     */
    public int getExpandedVisibility() {
        return expanded
               ? View.VISIBLE
               : View.GONE;
    }
}
