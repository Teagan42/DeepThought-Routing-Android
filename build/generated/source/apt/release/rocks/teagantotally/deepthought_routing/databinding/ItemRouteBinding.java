package rocks.teagantotally.deepthought_routing.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
public abstract class ItemRouteBinding extends ViewDataBinding {
    public final android.widget.TextView parametersLabel;
    public final android.widget.TextView permissionsLabel;
    public final android.widget.TextView routeClassPath;
    public final android.widget.TextView routeDescription;
    public final android.support.v7.widget.RecyclerView routeParameters;
    public final android.support.v7.widget.RecyclerView routePermissions;
    public final android.widget.TextView routeUri;
    // variables
    protected rocks.teagantotally.deepthought_routing.activities.viewmodels.RouteViewModel mRoute;
    protected ItemRouteBinding(android.databinding.DataBindingComponent bindingComponent, android.view.View root_, int localFieldCount
        , android.widget.TextView parametersLabel
        , android.widget.TextView permissionsLabel
        , android.widget.TextView routeClassPath
        , android.widget.TextView routeDescription
        , android.support.v7.widget.RecyclerView routeParameters
        , android.support.v7.widget.RecyclerView routePermissions
        , android.widget.TextView routeUri
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.parametersLabel = parametersLabel;
        this.permissionsLabel = permissionsLabel;
        this.routeClassPath = routeClassPath;
        this.routeDescription = routeDescription;
        this.routeParameters = routeParameters;
        this.routePermissions = routePermissions;
        this.routeUri = routeUri;
    }
    //getters and abstract setters
    public abstract void setRoute(rocks.teagantotally.deepthought_routing.activities.viewmodels.RouteViewModel Route);
    public rocks.teagantotally.deepthought_routing.activities.viewmodels.RouteViewModel getRoute() {
        return mRoute;
    }
    public static ItemRouteBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemRouteBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemRouteBinding bind(android.view.View view) {
        return null;
    }
    public static ItemRouteBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemRouteBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemRouteBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}