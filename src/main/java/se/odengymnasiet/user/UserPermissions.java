package se.odengymnasiet.user;

import org.bson.Document;
import se.odengymnasiet.DocumentSerializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Demonstration of all {@link User}s permissions.
 */
public class UserPermissions implements DocumentSerializable, Permissible,
        Iterable<Map.Entry<Permission, PermissionValue>> {

    private final User user;
    private final Map<Permission, PermissionValue> values = new HashMap<>();

    public UserPermissions(User user) {
        this.user = user;
    }

    @Override
    public Iterator<Map.Entry<Permission, PermissionValue>> iterator() {
        return this.values.entrySet().iterator();
    }

    @Override
    public PermissionValue permission(Permission permission) {
        if (this.user.isSuspended()) {
            return PermissionValue.DENY;
        } else if (this.user.isRoot()) {
            return PermissionValue.ALLOW;
        }

        PermissionValue defaultValue = permission.getDefaultValue();
        if (defaultValue == null) {
            defaultValue = PermissionValue.ABSTAIN;
        }

        return this.values.getOrDefault(permission, defaultValue);
    }

    public void addPermission(Permission permission, PermissionValue value) {
        this.values.put(permission, value);
    }

    public Set<Permission> getPermissions() {
        return this.values.keySet();
    }

    @Override
    public Document serialize(Document data) {
        this.values.forEach((permission, value) ->
                data.put(permission.getName(), value.toBoolean()));
        return data;
    }

    public static UserPermissions deserialize(User user, Document data) {
        UserPermissions permissions = new UserPermissions(user);

        data.entrySet().forEach(entry -> {
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                Permission permission = Permission.of(entry.getKey());
                PermissionValue permissionValue =
                        PermissionValue.fromBoolean((boolean) value);
                permissions.addPermission(permission, permissionValue);
            }
        });

        return permissions;
    }
}
