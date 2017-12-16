package se.odengymnasiet.article;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

import java.util.Collection;
import java.util.stream.Collectors;

public interface ArticleRepository extends Repository<Article> {

    ArticleRepository LOCAL = new LocalArticleRepository();

    Collection<Article> findAllByStartingPath(String startingPath);

    Article findByPath(String path);
}

@RepositoryHandler(ArticleRepository.class)
class LocalArticleRepository extends LocalRepository<Article>
                             implements ArticleRepository {

    @Override
    public Collection<Article> findAllByStartingPath(String startingPath) {
        return this.container.values().stream()
                .filter(article -> article.getPath().startsWith(startingPath))
                .collect(Collectors.toList());
    }

    @Override
    public Article findByPath(String path) {
        return this.container.values().stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst().get();
    }
}
