package rocks.teagantotally.deepthought_routing.interfaces;

import android.support.annotation.IdRes;

/**
 * Created by tglenn on 9/30/17.
 */

public interface FragmentContainer {
    /**
     * Get the identifier of the view that is to contain the fragment to load
     *
     * @return View identifier
     */
    @IdRes
    int getContainerViewId();
}
