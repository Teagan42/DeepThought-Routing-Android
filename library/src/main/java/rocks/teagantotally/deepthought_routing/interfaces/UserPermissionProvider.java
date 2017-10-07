package rocks.teagantotally.deepthought_routing.interfaces;

import java.util.Set;

/**
 * Created by tglenn on 10/2/17.
 */

public interface UserPermissionProvider<UserIdentifierType> {
    /**
     * Get the permissions for the given user
     *
     * @param userIdentifier The identifier for the user
     * @return The set of permissions the user has
     */
    Set<String> getPermissionsForUser(UserIdentifierType userIdentifier);

    /**
     * Gets the user identifier to use for route authentication
     *
     * @return The user identifier
     */
    UserIdentifierType getUserIdentifier();
}
