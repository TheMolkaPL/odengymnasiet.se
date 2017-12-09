package se.odengymnasiet.index;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

public class Article extends Model {

    public static final Article NULL = new NullArticle();

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_TEXT = "text";

    private String title;
    private String path;
    private String text;

    public Article() {
        super();
    }

    public Article(ObjectId id) {
        super(id);
    }

    public Article(Document data) {
        super(data);
    }

    public String getTitle() {
        return this.title;
    }

    public String getPath() {
        return this.path;
    }

    public String getText() {
        return this.text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_TITLE, this.getTitle());
        data.put(FIELD_PATH, this.getPath());
        data.put(FIELD_TEXT, this.getText());
        return super.serialize(data);
    }

    public static Article deserialize(Document data) {
        Article article = new Article(data);
        article.setTitle(data.getString(FIELD_TITLE));
        article.setPath(data.getString(FIELD_PATH));
        article.setText(data.getString(FIELD_TEXT));
        return article;
    }
}

class NullArticle extends Article {

    public static final ObjectId ID = new ObjectId("5a2080146f7b3e68f34a0f87");

    public NullArticle() {
        super(ID);
    }

    @Override
    public String getTitle() {
        return "Under konstruktion";
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String getText() {
        return "<h2 class=\"text-center\">Sidan är under konstruktion!</h2>";
    }
}
