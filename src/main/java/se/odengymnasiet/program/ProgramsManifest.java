package se.odengymnasiet.program;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.index.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Programs",
              parent = IndexManifest.class,
              master = ProgramsController.class,
              route = "/programs")
public class ProgramsManifest extends Manifest<ProgramsController> {

    private ArticleRepository articleRepository;
    private ProgramRepository programRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.programRepository = repositories.of(ProgramRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public ProgramRepository getProgramRepository() {
        return this.programRepository;
    }
}
