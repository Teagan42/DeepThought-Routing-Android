package rocks.teagantotally.deepthought_routing.lifecycle;

import rocks.teagantotally.deepthought_routing.RoutedActivity;

/**
 * Created by tglenn on 10/17/17.
 */

public interface RoutedActivityLifecycleCallbacks {
    /**
     * Callback for when a routed activity is resumed
     *
     * @param routedActivity The routed activity being resumed
     */
    void onActivityResumed(RoutedActivity routedActivity);

    /**
     * Callback for when a routed activity is stopped
     *
     * @param routedActivity The routed activity being stopped
     */
    void onActivityStopped(RoutedActivity routedActivity);
}
