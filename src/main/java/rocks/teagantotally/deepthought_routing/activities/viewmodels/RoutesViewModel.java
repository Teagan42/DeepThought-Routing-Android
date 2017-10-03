package rocks.teagantotally.deepthought_routing.activities.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import rocks.teagantotally.deepthought_routing.BR;
import rocks.teagantotally.deepthought_routing.R;
import rocks.teagantotally.deepthought_routing.Route;
import rocks.teagantotally.deepthoughtrecycler.AbstractItemBinder;
import rocks.teagantotally.deepthoughtrecycler.ClickHandler;
import rocks.teagantotally.deepthoughtrecycler.ItemBinder;

/**
 * Created by tglenn on 10/2/17.
 */

public class RoutesViewModel
          extends BaseObservable {
    private static final AbstractItemBinder<RouteViewModel> routeItemBinder =
              new ItemBinder<>(BR.route,
                               R.layout.item_route);
    private static final ClickHandler<RouteViewModel> routeClickHandler =
              new ClickHandler<RouteViewModel>() {
                  @Override
                  public void onClick(RouteViewModel item) {
                      item.toggleExpanded();
                  }
              };
    private Collection<RouteViewModel> routes = new LinkedList<>();
    private RecyclerView.LayoutManager routesLayoutManager;

    public RoutesViewModel(@NonNull Context context,
                           Collection<Route> routes) {
        Objects.requireNonNull(context,
                               "Context cannot be null");
        for (Route route : routes) {
            this.routes.add(new RouteViewModel(context,
                                               route));
        }
        routesLayoutManager = new LinearLayoutManager(context,
                                                      LinearLayoutManager.VERTICAL,
                                                      false) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
    }

    /**
     * @return The item binder for route views
     */
    public AbstractItemBinder<RouteViewModel> getRouteItemBinder() {
        return routeItemBinder;
    }

    /**
     * @return The click handler for routes
     */
    public ClickHandler<RouteViewModel> getRouteClickHandler() {
        return routeClickHandler;
    }

    /**
     * @return The route view models
     */
    public Collection<RouteViewModel> getRoutes() {
        return routes;
    }

    /**
     * @return The layout manager for the routes view
     */
    public RecyclerView.LayoutManager getRoutesLayoutManager() {
        return routesLayoutManager;
    }
}
