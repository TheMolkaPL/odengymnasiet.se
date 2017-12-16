package se.odengymnasiet.user;

public interface Permissible {

    default boolean hasPermission(Permission permission) {
        return this.permission(permission).access();
    }

    default boolean hasPermission(String permission) {
        return this.hasPermission(Permission.of(permission));
    }

    PermissionValue permission(Permission permission);

    default PermissionValue permission(String permission) {
        return this.permission(Permission.of(permission));
    }
}
