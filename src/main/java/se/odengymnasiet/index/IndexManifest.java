package se.odengymnasiet.index;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.falafel.FalafelRepository;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.program.ProgramRepository;

@Index
@ManifestInfo(name = "Index",
              parent = IndexManifest.class,
              master = IndexController.class)
public class IndexManifest extends Manifest<IndexController> {

    private ArticleRepository articleRepository;
    private FalafelRepository falafelRepository;
    private MarketingRepository marketingRepository;
    private OpenHouseRepository openHouseRepository;
    private ProgramRepository programRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.falafelRepository = repositories.of(FalafelRepository.class);
        this.marketingRepository = repositories.of(MarketingRepository.class);
        this.openHouseRepository = repositories.of(OpenHouseRepository.class);
        this.programRepository = repositories.of(ProgramRepository.class);
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

    public OpenHouseRepository getOpenHouseRepository() {
        return this.openHouseRepository;
    }

    public ProgramRepository getProgramRepository() {
        return this.programRepository;
    }
}
