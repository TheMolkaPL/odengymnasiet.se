package se.odengymnasiet.program;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a education program in this school.
 */
public class Program extends Model implements Comparable<Program> {

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_SUBTITLE = "subtitle";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_MARKETING = "marketing";
    public static final String FIELD_RECOMMENDED = "recommended";
    public static final String FIELD_OPEN = "open";
    public static final String FIELD_FILES = "files";

    private String title;
    private String subtitle;
    private String path;
    private String icon;
    private int priority;
    private String marketing;
    private boolean recommended;
    private boolean open;
    private List<ProgramFile> files;

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

    public int getPriority() {
        return this.priority;
    }

    public String getMarketing() {
        return this.marketing;
    }

    public List<ProgramFile> getFiles() {
        return this.files;
    }

    public boolean isRecommended() {
        return this.recommended;
    }

    public boolean isOpen() {
        return this.open;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setMarketing(String marketing) {
        this.marketing = marketing;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setFiles(List<ProgramFile> files) {
        this.files = files;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_TITLE, this.getTitle());
        data.put(FIELD_SUBTITLE, this.getSubtitle());
        data.put(FIELD_PATH, this.getPath());
        data.put(FIELD_ICON, this.getIcon());
        data.put(FIELD_PRIORITY, this.getPriority());
        data.put(FIELD_MARKETING, this.getMarketing());
        data.put(FIELD_RECOMMENDED, this.isRecommended());
        data.put(FIELD_OPEN, this.isOpen());
        data.put(FIELD_FILES, this.getFiles());
        return super.serialize(data);
    }

    public static Program deserialize(Document data) {
        Program program = new Program(data);
        program.setTitle(data.getString(FIELD_TITLE));
        program.setSubtitle(data.getString(FIELD_SUBTITLE));
        program.setPath(data.getString(FIELD_PATH));
        program.setIcon(data.getString(FIELD_ICON));
        program.setPriority(data.getInteger(FIELD_PRIORITY));
        program.setMarketing(data.getString(FIELD_MARKETING));
        program.setRecommended(data.getBoolean(FIELD_RECOMMENDED));
        program.setOpen(data.getBoolean(FIELD_OPEN));
        program.setFiles((List<ProgramFile>) data
                .get(FIELD_FILES, ArrayList.class).stream()
                .map(o -> ProgramFile.deserialize((Document) o))
                .sorted(new Comparator<ProgramFile>() {
                    @Override
                    public int compare(ProgramFile o1, ProgramFile o2) {
                        return o1.compareTo(o2);
                    }
                })
                .collect(Collectors.toList()));
        return program;
    }
}
