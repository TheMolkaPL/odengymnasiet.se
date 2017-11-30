package se.odengymnasiet.index;

import org.apache.commons.lang3.Validate;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

public class Marketing extends Model {

    public static final Marketing NULL = new NullMarketing();

    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_FIXED = "fixed";
    public static final String FIELD_POSITION = "position";
    public static final String FIELD_DEPLOYED = "deployed";

    private String image;
    private boolean fixed;
    private double position;
    private boolean deployed;

    public Marketing() {
        super();
    }

    public Marketing(ObjectId id) {
        super(id);
    }

    public Marketing(Document data) {
        super(data);
    }

    public String getImage() {
        return this.image;
    }

    public double getPosition() {
        return this.position;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void setPosition(double position) {
        Validate.inclusiveBetween(0D, 1D, position);
        this.position = position;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_IMAGE, this.getImage());
        data.put(FIELD_FIXED, this.isFixed());
        data.put(FIELD_POSITION, this.getPosition());
        data.put(FIELD_DEPLOYED, this.isDeployed());
        return super.serialize(data);
    }

    public static Marketing deserialize(Document data) {
        Marketing marketing = new Marketing(data);
        marketing.setImage(data.getString(FIELD_IMAGE));
        marketing.setFixed(data.getBoolean(FIELD_FIXED));
        marketing.setPosition(data.getDouble(FIELD_POSITION));
        marketing.setDeployed(data.getBoolean(FIELD_DEPLOYED));
        return marketing;
    }
}

class NullMarketing extends Marketing {

    public static final ObjectId ID = new ObjectId("5a2080146f7b3e68f34a0f86");

    public NullMarketing() {
        super(ID);
    }

    @Override
    public String getImage() {
        return "";
    }

    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public double getPosition() {
        return 0.5D;
    }

    @Override
    public boolean isDeployed() {
        return true;
    }

    @Override
    public void setId(ObjectId id) {
    }
}
