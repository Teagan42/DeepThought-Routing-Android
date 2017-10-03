package rocks.teagantotally.deepthought_routing.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
public abstract class ItemParameterBinding extends ViewDataBinding {
    public final android.widget.TextView paramName;
    public final android.widget.TextView paramNameCollapsed;
    // variables
    protected rocks.teagantotally.deepthought_routing.activities.viewmodels.ParameterViewModel mParam;
    protected ItemParameterBinding(android.databinding.DataBindingComponent bindingComponent, android.view.View root_, int localFieldCount
        , android.widget.TextView paramName
        , android.widget.TextView paramNameCollapsed
    ) {
        super(bindingComponent, root_, localFieldCount);
        this.paramName = paramName;
        this.paramNameCollapsed = paramNameCollapsed;
    }
    //getters and abstract setters
    public abstract void setParam(rocks.teagantotally.deepthought_routing.activities.viewmodels.ParameterViewModel Param);
    public rocks.teagantotally.deepthought_routing.activities.viewmodels.ParameterViewModel getParam() {
        return mParam;
    }
    public static ItemParameterBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemParameterBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemParameterBinding bind(android.view.View view) {
        return null;
    }
    public static ItemParameterBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemParameterBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemParameterBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}