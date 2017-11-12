package se.odengymnasiet;

public class Program implements Comparable<Program> {
    private String title;
    private String subtitle;
    private String path;
    private String icon;
    private String description;
    private short priority;

    @Override
    public int compareTo(Program o) {
        return Short.compare(this.getPriority(), o.getPriority());
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public String getPath() {
        return this.path;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getDescription() {
        return this.description;
    }

    public short getPriority() {
        return this.priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }
}
