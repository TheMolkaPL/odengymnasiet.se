package se.odengymnasiet.index;

import org.apache.commons.lang3.Validate;
import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.mongo.Model;

public class Marketing extends Model {

    public static final Marketing NULL = new NullMarketing();

    public static final String FIELD_IMAGE = "image";
    public static final String FIELD_POSITION = "position";
    public static final String FIELD_DEPLOYED = "deployed";

    private String image;
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

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setImage(String image) {
        this.image = image;
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
        data.put(FIELD_POSITION, this.getPosition());
        data.put(FIELD_DEPLOYED, this.isDeployed());
        return super.serialize(data);
    }

    public static Marketing deserialize(Document data) {
        Marketing marketing = new Marketing(data);
        marketing.setImage(data.getString(FIELD_IMAGE));
        marketing.setPosition(data.getDouble(FIELD_POSITION));
        marketing.setDeployed(data.getBoolean(FIELD_DEPLOYED));
        return marketing;
    }

    private static class NullMarketing extends Marketing {
        @Override
        public String getImage() {
            return "";
        }

        @Override
        public double getPosition() {
            return 0.5D;
        }

        @Override
        public boolean isDeployed() {
            return true;
        }
    }
}
