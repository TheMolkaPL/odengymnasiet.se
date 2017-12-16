package se.odengymnasiet.article;

import org.apache.commons.lang3.StringUtils;

public class ArticlePaths {

    private static final String ABOUT = "about";
    private static final String CONTACT = "contact";
    private static final String PROGRAMS = "programs";
    private static final String STUDENTS = "students";

    private ArticlePaths() {
    }

    public static String about() {
        return ABOUT;
    }

    public static String about(String... pages) {
        return path(about(), pages);
    }

    public static String contact() {
        return CONTACT;
    }

    public static String contact(String... pages) {
        return path(contact(), pages);
    }

    public static String programs() {
        return PROGRAMS;
    }

    public static String programs(String... pages) {
        return path(programs(), pages);
    }

    public static String students() {
        return STUDENTS;
    }

    public static String students(String... pages) {
        return path(students(), pages);
    }

    private static String path(String root, String... pages) {
        return pages != null ? root + "/" + StringUtils.join(pages, "/") : root;
    }
}
