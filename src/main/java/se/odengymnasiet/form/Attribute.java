package se.odengymnasiet.form;

public class Attribute {

    private final String attribute;
    private boolean required;

    private Attribute(String attribute) {
        this(attribute, false);
    }

    private Attribute(String attribute, boolean required) {
        this.attribute = attribute;
        this.required = required;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public static Attribute getAttribute(String attribute) {
        return getAttribute(attribute, false);
    }

    public static Attribute getAttribute(String attribute, boolean required) {
        return new Attribute(attribute, false);
    }

    public static Attribute require(String attribute) {
        Attribute value = getAttribute(attribute);
        value.setRequired(true);
        return value;
    }

    public static Attribute optional(String attribute) {
        Attribute value = getAttribute(attribute);
        value.setRequired(false);
        return value;
    }
}
