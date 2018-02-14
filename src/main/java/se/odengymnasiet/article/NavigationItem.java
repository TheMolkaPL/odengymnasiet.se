package se.odengymnasiet.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Maybe not the best designed class... It stores information about different
 * pages with articles.
 */
public class NavigationItem implements Comparable<NavigationItem> {

    private final Article article;
    private final String target;
    private final boolean active;

    public NavigationItem(Article article, String target) {
        this(article, target, false);
    }

    public NavigationItem(Article article, String target, boolean active) {
        this.article = article;
        this.target = target;
        this.active = active;
    }

    @Override
    public int compareTo(NavigationItem o) {
        return this.getArticle().compareTo(o.getArticle());
    }

    public Article getArticle() {
        return this.article;
    }

    public String getTarget() {
        return this.target;
    }

    public boolean isActive() {
        return this.active;
    }

    public static List<NavigationItem> list(ArticleRepository repository,
                                            String path,
                                            String now,
                                            String indexTitle) {
        List<Article> articles = new ArrayList<>(
                repository.findAllByStartingPath(path));

        Optional<Article> indexMaybe = articles.stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst();

        Article index;
        if (indexMaybe.isPresent()) {
            index = indexMaybe.get();
        } else {
            articles.add(index = Article.copyOf(Article.NULL));
        }

        index.setPath(path);
        index.setPriority(Integer.MAX_VALUE);
        index.setTitle(indexTitle);

        List<NavigationItem> items = new ArrayList<>();
        articles.forEach(article -> {
            String target = article.getPath();
            boolean active = article.getPath().equals(now);

            if (article.getPath().equals(path)) {
                target = target.substring(0, target.length() - 1);
            }

            items.add(new NavigationItem(article, target, active));
        });

        Collections.sort(items);
        return items;
    }
}
