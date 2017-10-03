package rocks.teagantotally.deepthought_routing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import rocks.teagantotally.deepthought_routing.activities.RouteDisplayActivity;
import rocks.teagantotally.deepthought_routing.activities.RouteProcessingActivity;
import rocks.teagantotally.deepthought_routing.annotations.ActivityRouteDefinition;
import rocks.teagantotally.deepthought_routing.annotations.FragmentRouteDefinition;
import rocks.teagantotally.deepthought_routing.exceptions.RouteNotAuthorizedException;
import rocks.teagantotally.deepthought_routing.exceptions.RouteNotFoundException;
import rocks.teagantotally.deepthought_routing.helpers.ClassHelper;
import rocks.teagantotally.deepthought_routing.interfaces.RouteListener;
import rocks.teagantotally.deepthought_routing.interfaces.UserPermissionProvider;
import timber.log.Timber;

/**
 * Created by tglenn on 9/29/17.
 */

public class Router<UserIdentifierType> {
    private static final String CLASS_NAME = Router.class.getName();
    private static final String ROUTES_ROUTE_DEFINITION =
              "Displays all registerd routes and their descriptions";
    public static final String EXTRA_ROUTED = CLASS_NAME + ".routed";
    public static final String EXTRA_FRAGMENT = CLASS_NAME + ".fragment";
    public static final String EXTRA_EXTRAS = CLASS_NAME + ".extras";
    public static final String EXTRA_ROUTE = CLASS_NAME + ".route";

    public static class Builder<UserIdentifierType> {
        private Context context;
        private Set<RouteListener> routeListeners = new HashSet<>();
        private Set<String> packagesToInclude = new HashSet<>();
        private Set<Class<? extends RoutedActivity>> activityClassesToRoute = new HashSet<>();
        private Set<Class<? extends Fragment>> fragmentClassesToRoute = new HashSet<>();
        private UserPermissionProvider<UserIdentifierType> permissionProvider = null;
        private Uri routesUri;

        private Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the uri to the activity to display the routes
         *
         * @param routesUri The uri
         * @return This builder
         */
        public Builder<UserIdentifierType> setRoutesUri(Uri routesUri) {
            this.routesUri = routesUri;

            return this;
        }

        /**
         * Add packages to automatically load
         *
         * @param packages Package names
         * @return This builder
         */
        public Builder<UserIdentifierType> addPackageToAutoLoad(String... packages) {
            if (packages == null || packages.length == 0) {
                return this;
            }

            Collections.addAll(packagesToInclude,
                               packages);

            return this;
        }

        /**
         * Add packages to automatically load
         *
         * @param packages Package
         * @return This builder
         */
        public Builder<UserIdentifierType> addPackageToAutoLoad(Package... packages) {
            if (packages == null || packages.length == 0) {
                return this;
            }

            for (Package packageToLoad : packages) {
                packagesToInclude.add(packageToLoad.getName());
            }

            return this;
        }

        /**
         * Sets the user permission provider
         *
         * @param permissionProvider The user permission provider implementation
         * @return This builder
         */
        public Builder<UserIdentifierType> setUserPermissionProvider(
                  UserPermissionProvider<UserIdentifierType> permissionProvider) {
            this.permissionProvider = permissionProvider;

            return this;
        }

        /**
         * Add a routed activity class to route to
         *
         * @param activityClasses Activity classes decorated with ActivityRouteDefinition
         * @return This builder
         */
        public Builder<UserIdentifierType> addActivityClassToRoute(
                  Class<? extends RoutedActivity>... activityClasses) {
            if (activityClasses == null || activityClasses.length == 0) {
                return this;
            }

            Collections.addAll(activityClassesToRoute,
                               activityClasses);

            return this;
        }

        /**
         * Add a routed fragment class to route to
         *
         * @param fragmentClasses Fragment classes decorated with FragmentRouteDefinition
         * @return This builder
         */
        public Builder<UserIdentifierType> addFragmentClassToRoute(
                  Class<? extends Fragment>... fragmentClasses) {
            if (fragmentClasses == null || fragmentClasses.length == 0) {
                return this;
            }

            Collections.addAll(fragmentClassesToRoute,
                               fragmentClasses);

            return this;
        }

        /**
         * Add a listener to the router
         *
         * @param routeListeners Route handled listeners
         * @return This builder
         */
        public Builder<UserIdentifierType> addRouteListener(RouteListener... routeListeners) {
            if (routeListeners == null || routeListeners.length == 0) {
                return this;
            }

            Collections.addAll(this.routeListeners,
                               routeListeners);

            return this;
        }

        /**
         * Builds the router object with all of the registered routes
         *
         * @return A new router
         */
        public Router<UserIdentifierType> build() {
            Router<UserIdentifierType> router =
                      new Router<>(loadRoutes(),
                                   permissionProvider,
                                   routeListeners);
            if (routesUri != null) {
                RouteDisplayActivity.router = router;
            }

            return router;
        }

        //region Route building

        private Set<Route> loadRoutes() {
            Set<Route> routes = new HashSet<>();

            if (routesUri != null) {
                Route routesRoute =
                          new Route(routesUri.toString(),
                                    RouteDisplayActivity.class,
                                    null,
                                    ROUTES_ROUTE_DEFINITION,
                                    null,
                                    null,
                                    null);
                routes.add(routesRoute);
            }

            addAnnotatedClassesFromContext();

            routes.addAll(loadActivityRoutesFromClasses());
            routes.addAll(loadFragmentRoutesFromClasses());

            return routes;
        }

        @SuppressWarnings("unchecked")
        private void addAnnotatedClassesFromContext() {
            Set<Class<?>> annotatedClasses =
                      ClassHelper.getClassesWithAnnotation(
                                context,
                                packagesToInclude,
                                ActivityRouteDefinition.class,
                                FragmentRouteDefinition.class);

            for (Class<?> annotatedClass : annotatedClasses) {
                if (AppCompatActivity.class.isAssignableFrom(annotatedClass)) {
                    activityClassesToRoute.add((Class<? extends RoutedActivity>) annotatedClass);
                } else if (Fragment.class.isAssignableFrom(annotatedClass)) {
                    fragmentClassesToRoute.add((Class<? extends Fragment>) annotatedClass);
                }
            }
        }

        private Route loadActivityRoute(Class<? extends AppCompatActivity> activityClass) {
            ActivityRouteDefinition routeDefinition =
                      activityClass.getAnnotation(ActivityRouteDefinition.class);

            if (routeDefinition == null) {
                Timber.d("%s is not decorated with %s",
                         activityClass.getName(),
                         ActivityRouteDefinition.class.getSimpleName());

                return null;
            }

            return new Route(activityClass,
                             routeDefinition);
        }

        private Route loadFragmentRoute(Class<? extends Fragment> fragmentClass) {
            FragmentRouteDefinition routeDefinition =
                      fragmentClass.getAnnotation(FragmentRouteDefinition.class);

            if (routeDefinition == null) {
                Timber.d("%s is not decorated with %s",
                         fragmentClass.getName(),
                         FragmentRouteDefinition.class.getSimpleName());

                return null;
            }

            return new Route(fragmentClass,
                             routeDefinition);
        }

        private Set<Route> loadActivityRoutesFromClasses() {
            Set<Route> routes = new HashSet<>();

            for (Class<? extends AppCompatActivity> activityClass : activityClassesToRoute) {
                Route route = loadActivityRoute(activityClass);
                if (route == null) {
                    continue;
                }

                routes.add(route);
            }

            return routes;
        }

        private Set<Route> loadFragmentRoutesFromClasses() {
            Set<Route> routes = new HashSet<>();

            for (Class<? extends Fragment> fragmentClass : fragmentClassesToRoute) {
                Route route = loadFragmentRoute(fragmentClass);
                if (route == null) {
                    continue;
                }

                routes.add(route);
            }

            return routes;
        }

        //endregion
    }

    /**
     * Create a new router builder
     *
     * @param context Context
     * @return The new router builder
     */
    public static Builder builder(@NonNull Context context) {
        Objects.requireNonNull(context,
                               "Context cannot be null");
        return new Builder(context);
    }

    private Set<Route> routes = new HashSet<>();
    private Set<RouteListener> routeListeners = new HashSet<>();
    private UserPermissionProvider<UserIdentifierType> permissionProvider;

    Router(Set<Route> routes,
           UserPermissionProvider<UserIdentifierType> permissionProvider,
           Set<RouteListener> routeListeners) {
        this.routes.addAll(routes);
        this.routeListeners.addAll(routeListeners);
        this.permissionProvider = permissionProvider;

        RouteProcessingActivity.setRouter(this);
    }

    /**
     * @return A copy of the routes list
     */
    public List<Route> getRoutes() {
        List<Route> clone = new LinkedList<>(routes);

        return clone;
    }

    /**
     * Try to route given an intent
     *
     * @param context Context
     * @param intent  Intent to handle
     * @return Whether the route was handled
     */
    public boolean handleIntent(Context context,
                                Intent intent) {
        Uri requestedUri = intent.getData();

        if (requestedUri == null) {
            Timber.d("Requested uri is null");
            return false;
        }

        return handleUri(context,
                         requestedUri,
                         intent.getFlags());
    }

    /**
     * Try to route the given uri
     *
     * @param context      Context
     * @param requestedUri Uri to handle
     * @return Whether the uri was handled
     */
    public boolean handleUri(Context context,
                             Uri requestedUri) {
        return handleUri(context,
                         requestedUri,
                         0);
    }

    /**
     * Try to route the given uri
     *
     * @param context      Context
     * @param requestedUri Uri to handle
     * @param intentFlags  Flags to add to the intent
     * @return Whether the uri was handled
     */
    public boolean handleUri(Context context,
                             Uri requestedUri,
                             int intentFlags) {
        UserIdentifierType userIdentifier;
        Set<String> userPermissions = new HashSet<>();

        if (permissionProvider != null) {
            userIdentifier = permissionProvider.getUserIdentifier();
            userPermissions = permissionProvider.getPermissionsForUser(userIdentifier);
        }

        try {
            for (Route route : routes) {
                Bundle extras = route.matchUri(requestedUri);
                if (extras == null) {
                    continue;
                }

                if (!route.getRequiredPermissions()
                          .isEmpty() && permissionProvider == null) {
                    throw new RouteNotAuthorizedException(requestedUri,
                                                          route);
                } else if (!userPermissions.containsAll(route.getRequiredPermissions())) {
                    throw new RouteNotAuthorizedException(requestedUri,
                                                          route);
                }

                Intent routeIntent = new Intent(context,
                                                route.getActivityClass());
                extras.putSerializable(EXTRA_FRAGMENT,
                                       route.getFragmentClass());
                extras.putString(EXTRA_ROUTE,
                                 requestedUri.toString());
                extras.putBoolean(EXTRA_ROUTED,
                                  true);
                routeIntent.putExtras(extras);
                routeIntent.addFlags(intentFlags);

                context.startActivity(routeIntent);

                notifyListeners(route,
                                extras);

                return true;
            }

            throw new RouteNotFoundException(requestedUri);
        } catch (RouteNotFoundException e) {
            notifyListeners(e.getRequestedUri());
        } catch (RouteNotAuthorizedException e) {
            notifyListeners(e.getRequestedUri(),
                            e.getRoute()
                             .getRequiredPermissions());
        }

        return false;
    }

    private void notifyListeners(Uri uri) {
        for (RouteListener listener : routeListeners) {
            listener.onRouteNotFound(uri);
        }
    }

    private void notifyListeners(Uri uri,
                                 Set<String> requiredPermissions) {
        for (RouteListener listener : routeListeners) {
            listener.onRouteNotAuthorized(uri,
                                          requiredPermissions);
        }
    }

    private void notifyListeners(Route route,
                                 Bundle extras) {
        for (RouteListener listener : routeListeners) {
            listener.onRouteHandled(route,
                                    extras);
        }
    }
}
