package se.odengymnasiet.admin;

import org.apache.commons.lang3.StringUtils;

public class Topic implements Comparable<Topic> {

    private final String path;
    private final String name;

    private Topic(String path, String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public int compareTo(Topic o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    public static Topic create(String path, String name) {
        return new Topic(normalizePath(path), StringUtils.capitalize(name));
    }

    public static String normalizePath(String input) {
        return input.toLowerCase();
    }
}
