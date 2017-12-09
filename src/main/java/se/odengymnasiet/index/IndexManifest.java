package se.odengymnasiet.index;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.student.FalafelRepository;

@Index
@ManifestInfo(name = "Index",
              parent = IndexManifest.class,
              master = IndexController.class)
public class IndexManifest extends Manifest<IndexController> {

    private ArticleRepository articleRepository;
    private FalafelRepository falafelRepository;
    private MarketingRepository marketingRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.falafelRepository = repositories.of(FalafelRepository.class);
        this.marketingRepository = repositories.of(MarketingRepository.class);
    }

    @Override
    public void enable() {
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public FalafelRepository getFalafelRepository() {
        return this.falafelRepository;
    }

    public MarketingRepository getMarketingRepository() {
        return this.marketingRepository;
    }
}
