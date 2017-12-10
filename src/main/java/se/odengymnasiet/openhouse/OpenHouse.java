package se.odengymnasiet.openhouse;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class OpenHouse extends Model implements Comparable<OpenHouse> {

    public static final String FIELD_START = "start";
    public static final String FIELD_END = "end";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PROGRAMS = "programs";
    public static final String FIELD_DEPLOYED = "deployed";

    private Instant start;
    private Instant end;
    private String description;
    private List<ObjectId> programs;
    private boolean deployed;

    public OpenHouse() {
        super();
    }

    public OpenHouse(ObjectId id) {
        super(id);
    }

    public OpenHouse(Document data) {
        super(data);
    }

    @Override
    public int compareTo(OpenHouse o) {
        return this.getStart().compareTo(o.getStart());
    }

    public Instant getStart() {
        return this.start;
    }

    public LocalDateTime getStartTime() {
        return this.toDateTime(this.getStart());
    }

    public Instant getEnd() {
        return this.end;
    }

    public LocalDateTime getEndTime() {
        return this.toDateTime(this.getEnd());
    }

    public String getDescription() {
        return this.description;
    }

    public List<ObjectId> getPrograms() {
        return this.programs;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public boolean isArchived() {
        return Instant.now().isAfter(this.getStart());
    }

    public boolean isNotEnding() {
        return this.end == null;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrograms(List<ObjectId> programs) {
        this.programs = programs;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_START, this.getStart());
        data.put(FIELD_END, this.getEnd());
        data.put(FIELD_DESCRIPTION, this.getDescription());
        data.put(FIELD_PROGRAMS, this.getPrograms());
        data.put(FIELD_DEPLOYED, this.isDeployed());
        return super.serialize(data);
    }

    public static OpenHouse deserialize(Document data) {
        Instant end = null;
        if (data.containsKey(FIELD_END) && data.get(FIELD_END) != null) {
            end = data.get(FIELD_END, Instant.class);
        }

        OpenHouse openHouse = new OpenHouse(data);
        openHouse.setStart(data.get(FIELD_START, Instant.class));
        openHouse.setEnd(end);
        openHouse.setDescription(data.getString(FIELD_DESCRIPTION));
        openHouse.setPrograms(data.get(FIELD_PROGRAMS, List.class));
        openHouse.setDeployed(data.getBoolean(FIELD_DEPLOYED));
        return openHouse;
    }

    private LocalDateTime toDateTime(Instant instant) {
        return instant != null
                ? LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
                : null;
    }
}
