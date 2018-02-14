package se.odengymnasiet.form;

import java.util.Objects;

public class Namespace implements Comparable<Namespace> {

    private static final Namespace DEFAULT_NAMESPACE = getNamespace("_def_ns_");

    private final String namespace;

    private Namespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public int compareTo(Namespace o) {
        return this.namespace.compareToIgnoreCase(o.namespace);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Namespace &&
                Objects.equals(this.namespace, ((Namespace) obj).namespace);
    }

    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.namespace);
    }

    public static Namespace defaultNamespace() {
        return DEFAULT_NAMESPACE;
    }

    public static Namespace getNamespace(String namespace) {
        return new Namespace(namespace);
    }
}
