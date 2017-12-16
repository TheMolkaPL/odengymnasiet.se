package se.odengymnasiet.user;

import java.util.Objects;

public class Permission {

    private final String name;
    private PermissionValue defaultValue;

    private Permission(String name, PermissionValue defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Permission &&
                Objects.equals(this.name, ((Permission) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String getName() {
        return this.name;
    }

    public PermissionValue getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(PermissionValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static Permission of(String name) {
        return of(name, PermissionValue.ABSTAIN);
    }

    public static Permission of(String name, PermissionValue defaultValue) {
        return new Permission(name, defaultValue);
    }
}
