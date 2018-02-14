package se.odengymnasiet.user;

/**
 * Value of a permission which tells if the permission should be granted or not.
 */
public enum PermissionValue {

    ALLOW(true), DENY(false), ABSTAIN(false);

    private final boolean value;

    PermissionValue(boolean value) {
        this.value = value;
    }

    public PermissionValue getOpposite() {
        switch (this) {
            case ALLOW: return DENY;
            case DENY: return ALLOW;
            default: return ABSTAIN;
        }
    }

    public boolean access() {
        return this.toBoolean();
    }

    public boolean toBoolean() {
        return this.value;
    }

    public static PermissionValue fromBoolean(boolean value) {
        if (value) {
            return ALLOW;
        } else {
            return DENY;
        }
    }

    public static PermissionValue parseValue(Boolean value) {
        if (value == null) {
            return ABSTAIN;
        } else {
            return fromBoolean(value);
        }
    }
}
