package rocks.teagantotally.deepthought_routing.lifecycle;

import java.util.LinkedHashSet;
import java.util.Set;

import rocks.teagantotally.deepthought_routing.RoutedActivity;

/**
 * Created by tglenn on 10/17/17.
 */

public abstract class RoutedActivityLifecycleHandler {
    private static Set<RoutedActivityLifecycleCallbacks> callbacks = new LinkedHashSet<>();

    /**
     * Register a life cycle callback
     *
     * @param callbacks Implementation of the callbacks
     */
    public static void registerCallbacks(RoutedActivityLifecycleCallbacks callbacks) {
        RoutedActivityLifecycleHandler.callbacks.add(callbacks);
    }

    /**
     * Unregister a life cycle callback
     *
     * @param callbacks Implementation of the callbacks
     */
    public static void unregisterCallbacks(RoutedActivityLifecycleCallbacks callbacks) {
        RoutedActivityLifecycleHandler.callbacks.remove(callbacks);
    }

    /**
     * Notify callbacks of a routed activity being resumed
     *
     * @param routedActivity Routed activity that was resumed
     */
    public static void routedActivityResumed(RoutedActivity routedActivity) {
        for (RoutedActivityLifecycleCallbacks callbacks : RoutedActivityLifecycleHandler.callbacks) {
            callbacks.onActivityResumed(routedActivity);
        }
    }

    /**
     * Notify callbacks of a routed activity being stopped
     *
     * @param routedActivity Routed activity that was stopped
     */
    public static void routedActivityStopped(RoutedActivity routedActivity) {
        for (RoutedActivityLifecycleCallbacks callbacks : RoutedActivityLifecycleHandler.callbacks) {
            callbacks.onActivityStopped(routedActivity);
        }
    }
}
