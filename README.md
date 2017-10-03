#Deepthought Routing
##Purpose
Facilitates navigation around application through the use of universal resource idendifiers (Uri). Many applications need to implement deep-linking or Url interception, by routing throughout the application via Uri, both of those use cases are handled automatically.

##Self-Documenting
By using the decorator pattern, extras passed to the activity or fragment are readily documented inline with the code - no need to worry about documentation going stale.
 
##Self-Validating
Each route can take Url or Query parameters, by defining the parameter data type and whether that parameter is required, the router will automatically convert values passed in.

In addition to parameter validation, the router can accept a UserPermissionsProvider implementation and compare the set of permissions returned by that to the required permissions for the route, automatically blocking navigation to activities or fragments that they do not have permission to view.