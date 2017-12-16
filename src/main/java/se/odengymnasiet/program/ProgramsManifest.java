package se.odengymnasiet.program;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;
import se.odengymnasiet.openhouse.OpenHouseRepository;

@ManifestInfo(name = "Programs",
              parent = IndexManifest.class,
              master = ProgramsController.class,
              route = "/programs")
public class ProgramsManifest extends Manifest<ProgramsController> {

    private ArticleRepository articleRepository;
    private OpenHouseRepository openHouseRepository;
    private ProgramRepository programRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.openHouseRepository = repositories.of(OpenHouseRepository.class);
        this.programRepository = repositories.of(ProgramRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public OpenHouseRepository getOpenHouseRepository() {
        return this.openHouseRepository;
    }

    public ProgramRepository getProgramRepository() {
        return this.programRepository;
    }
}
