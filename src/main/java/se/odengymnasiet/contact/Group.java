package se.odengymnasiet.contact;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

public class Group extends Model implements Comparable<Group> {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_PRIORITY = "priority";

    private String name;
    private int priority;

    public Group() {
        super();
    }

    public Group(ObjectId id) {
        super(id);
    }

    public Group(Document data) {
        super(data);
    }

    @Override
    public int compareTo(Group o) {
        int compare = Integer.compare(this.getPriority(), o.getPriority());
        if (compare != 0) {
            return compare;
        }

        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_NAME, this.getName());
        data.put(FIELD_PRIORITY, this.getPriority());
        return super.serialize(data);
    }

    public static Group deserialize(Document data) {
        Group group = new Group(data);
        group.setName(data.getString(FIELD_NAME));
        group.setPriority(data.getInteger(FIELD_PRIORITY));
        return group;
    }
}
