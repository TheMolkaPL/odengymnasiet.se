package se.odengymnasiet.index;

import se.odengymnasiet.LocalRepository;
import se.odengymnasiet.Repository;
import se.odengymnasiet.RepositoryHandler;

public interface ArticleRepository extends Repository<Article> {

    ArticleRepository LOCAL = new LocalArticleRepository();

    Article findByPath(String path);
}

@RepositoryHandler(ArticleRepository.class)
class LocalArticleRepository extends LocalRepository<Article>
                             implements ArticleRepository {

    @Override
    public Article findByPath(String path) {
        return this.container.values().stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst().get();
    }
}
