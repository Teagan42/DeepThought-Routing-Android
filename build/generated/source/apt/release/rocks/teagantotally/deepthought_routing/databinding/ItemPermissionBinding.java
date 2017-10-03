package rocks.teagantotally.deepthought_routing.databinding;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
public abstract class ItemPermissionBinding extends ViewDataBinding {
    // variables
    protected java.lang.String mPermission;
    protected ItemPermissionBinding(android.databinding.DataBindingComponent bindingComponent, android.view.View root_, int localFieldCount
    ) {
        super(bindingComponent, root_, localFieldCount);
    }
    //getters and abstract setters
    public abstract void setPermission(java.lang.String Permission);
    public java.lang.String getPermission() {
        return mPermission;
    }
    public static ItemPermissionBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemPermissionBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemPermissionBinding bind(android.view.View view) {
        return null;
    }
    public static ItemPermissionBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemPermissionBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
    public static ItemPermissionBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        return null;
    }
}