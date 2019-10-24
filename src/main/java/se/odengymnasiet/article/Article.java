/*
 * Copyright 2019 Aleksander Jagiełło <themolkapl@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.odengymnasiet.article;

import org.bson.Document;
import org.bson.types.ObjectId;
import se.odengymnasiet.Model;

/**
 * Simple text holder which can be renderer as a side information or a page
 * content.
 */
public class Article extends Model implements Comparable<Article> {

    public static final Article NULL = new NullArticle();

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_PRIORITY = "priority";
    public static final String FIELD_TEXT = "text";

    private String title;
    private String path;
    private int priority;
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

    @Override
    public int compareTo(Article o) {
        int compare = Integer.compare(o.getPriority(), this.getPriority());
        if (compare != 0) {
            return compare;
        }

        return this.getPath().compareToIgnoreCase(o.getPath());
    }

    public String getTitle() {
        return this.title;
    }

    public String getPath() {
        return this.path;
    }

    public int getPriority() {
        return this.priority;
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Document serialize(Document data) {
        data.put(FIELD_TITLE, this.getTitle());
        data.put(FIELD_PATH, this.getPath());
        data.put(FIELD_PRIORITY, this.getPriority());
        data.put(FIELD_TEXT, this.getText());
        return super.serialize(data);
    }

    public static Article deserialize(Document data) {
        Article article = new Article(data);
        article.setTitle(data.getString(FIELD_TITLE));
        article.setPath(data.getString(FIELD_PATH));
        article.setPriority(data.getInteger(FIELD_PRIORITY));
        article.setText(data.getString(FIELD_TEXT));
        return article;
    }

    public static Article copyOf(Article copyOf) {
        Article article = new Article();
        article.title = copyOf.title;
        article.path = copyOf.path;
        article.priority = copyOf.priority;
        article.text = copyOf.text;
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
