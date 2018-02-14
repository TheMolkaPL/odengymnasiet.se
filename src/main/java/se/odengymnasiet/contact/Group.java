package se.odengymnasiet.contact;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

/**
 * A {@link Person} group which can hold as many {@link Person}s as you want.
 * Every group have its name, priority (so it is sorted property on the
 * /contact/staff page). The group must have its roleName, so we know what the
 * people work with.
 */
public class Group extends Model implements Comparable<Group> {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_ROLE_NAME = "role_name";
    public static final String FIELD_PRIORITY = "priority";

    private String name;
    private String roleName;
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
        int compare = Integer.compare(o.getPriority(), this.getPriority());
        if (compare != 0) {
            return compare;
        }

        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String getName() {
        return this.name;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_NAME, this.getName());
        data.put(FIELD_ROLE_NAME, this.getRoleName());
        data.put(FIELD_PRIORITY, this.getPriority());
        return super.serialize(data);
    }

    public static Group deserialize(Document data) {
        Group group = new Group(data);
        group.setName(data.getString(FIELD_NAME));
        group.setRoleName(data.getString(FIELD_ROLE_NAME));
        group.setPriority(data.getInteger(FIELD_PRIORITY));
        return group;
    }
}
