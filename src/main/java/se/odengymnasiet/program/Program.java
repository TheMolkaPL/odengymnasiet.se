package se.odengymnasiet.program;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

public class Program extends Model implements Comparable<Program> {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_SUBTITLE = "subtitle";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRIORITY = "priority";

    private String title;
    private String subtitle;
    private String path;
    private String icon;
    private String description;
    private int priority;

    public Program() {
        super();
    }

    public Program(ObjectId id) {
        super(id);
    }

    public Program(Document data) {
        super(data);
    }

    @Override
    public int compareTo(Program o) {
        int compare = Integer.compare(o.getPriority(), this.getPriority());
        if (compare != 0) {
            return compare;
        }

        return this.getTitle().compareToIgnoreCase(o.getTitle());
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

    public int getPriority() {
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_TITLE, this.getTitle());
        data.put(FIELD_SUBTITLE, this.getSubtitle());
        data.put(FIELD_PATH, this.getPath());
        data.put(FIELD_ICON, this.getIcon());
        data.put(FIELD_DESCRIPTION, this.getDescription());
        data.put(FIELD_PRIORITY, this.getPriority());
        return super.serialize(data);
    }

    public static Program deserialize(Document data) {
        Program program = new Program(data);
        program.setTitle(data.getString(FIELD_TITLE));
        program.setSubtitle(data.getString(FIELD_SUBTITLE));
        program.setPath(data.getString(FIELD_PATH));
        program.setIcon(data.getString(FIELD_ICON));
        program.setDescription(data.getString(FIELD_DESCRIPTION));
        program.setPriority(data.getInteger(FIELD_PRIORITY));
        return program;
    }
}
