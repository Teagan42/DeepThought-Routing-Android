package rocks.teagantotally.deepthought_routing.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
public abstract class ActivityRoutesBinding extends ViewDataBinding {
    public final android.support.v7.widget.RecyclerView routes;
    // variables
    protected rocks.teagantotally.deepthought_routing.activities.viewmodels.RoutesViewModel mRoutes;
    protected ActivityRoutesBinding(android.databinding.DataBindingComponent bindingComponent, android.view.View root_, int localFieldCount
        , android.support.v7.widget.RecyclerView routes
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.routes = routes;
    }
    //getters and abstract setters
    public abstract void setRoutes(rocks.teagantotally.deepthought_routing.activities.viewmodels.RoutesViewModel Routes);
    public rocks.teagantotally.deepthought_routing.activities.viewmodels.RoutesViewModel getRoutes() {
        return mRoutes;
    }
    public static ActivityRoutesBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityRoutesBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityRoutesBinding bind(android.view.View view) {
        return null;
    }
    public static ActivityRoutesBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ActivityRoutesBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ActivityRoutesBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}