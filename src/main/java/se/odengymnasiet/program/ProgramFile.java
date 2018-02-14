package se.odengymnasiet.program;

import org.bson.Document;
import se.odengymnasiet.DocumentSerializable;

/**
 * A file assets attached to a program (eg. a point schema PDF file).
 */
public class ProgramFile implements Comparable<ProgramFile>,
                                    DocumentSerializable {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_URL = "url";
    public static final String FIELD_FORMAT = "format";
    public static final String FIELD_SIZE = "size";

    private String name;
    private String url;
    private String format;
    private double size;

    public ProgramFile() {
    }

    @Override
    public int compareTo(ProgramFile o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public String getFormat() {
        return this.format;
    }

    public double getSize() {
        return this.size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_NAME, this.getName());
        data.put(FIELD_URL, this.getUrl());
        data.put(FIELD_FORMAT, this.getFormat());
        data.put(FIELD_SIZE, this.getSize());
        return data;
    }

    public static ProgramFile deserialize(Document data) {
        ProgramFile file = new ProgramFile();
        file.setName(data.getString(FIELD_NAME));
        file.setUrl(data.getString(FIELD_URL));
        file.setFormat(data.getString(FIELD_FORMAT));
        file.setSize(data.getDouble(FIELD_SIZE));
        return file;
    }
}
