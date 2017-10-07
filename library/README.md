# Deepthought Routing  
## Purpose  
Facilitates navigation around application through the use of universal resource idendifiers (Uri). Many applications need to implement deep-linking or Url interception, by routing throughout the application via Uri, both of those use cases are handled automatically.

### Self-Documenting  
By using the decorator pattern, extras passed to the activity or fragment are readily documented inline with the code - no need to worry about documentation going stale.

In addition to the decorations behaving as documentation, if you specify a routes uri when building the router, you can retrieve a list of all routes registered, the activity and/or fragment classes used, the parameter definitions and permissions required for each route.
 
### Self-Validating  
Each route can take Url or Query parameters, by defining the parameter data type and whether that parameter is required, the router will automatically convert values passed in.

In addition to parameter validation, the router can accept a UserPermissionsProvider implementation and compare the set of permissions returned by that to the required permissions for the route, automatically blocking navigation to activities or fragments that they do not have permission to view.

### Self-Building
Using the JVM class loader, the router can automatically find any and all routes (Activities annotated with ActivityRouteDefinition or Fragments annotated with FragmentRouteDefinition) from specified package prefixes; the autoloader will traverse the package tree only loading classes who are in the specified package or in a subpackage of those specified.

## Usage

### Router Builder
For a usable router, you can use this to get started:

```
Router router = Router.builder(context).build();
```

#### Autoloading
Autoloading of routes be a bit heavy handed - not to mention it will throw quite a few warnings as it traverses the package tree.

```
Router.builder(context)
      .addPackageToAutoload("rocks.teagantotally.android.app")
```

If you'd rather use `Package` objects:

```
Router.builder(context)
      .addPackageToAutoload(MyApplication.class.getPackage())
```

#### Specifying Routed Classes
If performance is key to your application, it is recommended you specify the annotated activities and fragments directly when building the router:

```
Router.builder(context)
      .addRoutedActivity(MainActivity.class)
      .addRoutedFragment(LoggedInLandingFragment.class)
```

#### Route Listeners
You may want to hook into and perform custom logic around the router. A `RouteListener` hooks into when a route has been handled, when a route is not found, when a route is unauthorized, or when a route is malformed (missing a required parameter or the parameter is of the wrong type). You can add as many listeners as you like to the router:

```
Router.builder(context)
      .addRouteListener(myRouteListener)
      .addRouteListener(userViewTracker)
```

#### Permission Validation
Some routes may require specific user permissions to access them, and the router will check these permissions prior to handling. The router will need to know what permissions the user has, the `UserPermissionProvider` interface provides two methods `getUserIdentifier` and `getPermissionsForUser`. Add your `UserPermissionProvider` to the router like so:

```
Router.builder(context)
      .setUserPermissionProvider(userServiceController)
```

The default route processing activity will handle the uri on an async thread, so your `UserPermissionProvider` can be blocking.

#### Displaying Registered Routes
If you would like to visualize the routes that are registered, simply set the `routesUri` when building the router:

```
Router.builder(context)
      .setRoutesUri("https://teagantotally.rocks/routes")
```

The `setRoutesUri` accepts either a `String` or `Uri` object. When the router handles this uri, it will take the user to a screen displaying all of the routes, the class path (Activity > Fragment), the descriptions, parameter definitions and permissions. It is highly recommended you only add this step of the builder inside `if (BuildConfig.DEBUG)` for security sake.

#### Building the Router
As a final step, invoke the `Router.Builder.build()` method, this is the method that converts the annotations into actual usable routes.

```
Router router = Router.builder()
                      //...
                      .build();
```

### Processing Uris
In your manifest, specify the activity that will process the uri intents, specifying the scheme(s), host(s) and path(s) to intercept:

```
<application
        ...>

    <activity android:name="rocks.teagantotally.deepthought_routing.activities.RouteProcessingActivity">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.BROWSABLE" />
            <category android:name="android.intent.category.DEFAULT" />

            <data
                android:host="teagantotally.rocks"
                android:scheme="https" />
        </intent-filter>
    </activity>

</application>
```

By default, Deepthought-Routing will process intents via the `RouteProcessingActivity`, but you can specify any activity you would like.