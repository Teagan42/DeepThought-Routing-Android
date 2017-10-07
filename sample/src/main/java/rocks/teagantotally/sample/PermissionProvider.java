package rocks.teagantotally.sample;

import java.util.HashSet;
import java.util.Set;

import rocks.teagantotally.deepthought_routing.interfaces.UserPermissionProvider;

/**
 * Created by tglenn on 10/7/17.
 */

public class PermissionProvider
          implements UserPermissionProvider<Integer> {
    public static boolean loggedIn = false;
    public static final String PERMISSION_ACCOUNT = "USER.ACCOUNT";

    /**
     * Get the permissions for the given user
     *
     * @param userIdentifier The identifier for the user
     * @return The set of permissions the user has
     */
    @Override
    public Set<String> getPermissionsForUser(Integer userIdentifier) {
        Set<String> permissions = new HashSet<>();
        if (loggedIn) {
            permissions.add(PERMISSION_ACCOUNT);
        }
        return permissions;
    }

    /**
     * Gets the user identifier to use for route authentication
     *
     * @return The user identifier
     */
    @Override
    public Integer getUserIdentifier() {
        return 1;
    }
}
