package se.odengymnasiet.article;

import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.admin.AdminManifest;
import se.odengymnasiet.admin.Topic;
import se.odengymnasiet.admin.TopicHolder;
import se.odengymnasiet.admin.TopicsManifest;

@ManifestInfo(name = "Admin-Articles",
              parent = TopicsManifest.class,
              master = ArticleAdminController.class,
              route = "articles")
public class ArticleAdminManifest extends AdminManifest<ArticleAdminController>
                                  implements TopicHolder {

    public static final Topic TOPIC = Topic.create("articles", "Artiklar");

    private ArticleRepository articleRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
    }

    @Override
    public Topic getTopic() {
        return TOPIC;
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }
}
