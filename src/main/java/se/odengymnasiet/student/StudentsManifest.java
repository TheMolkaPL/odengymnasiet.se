package se.odengymnasiet.student;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Students",
              parent = IndexManifest.class,
              master = StudentsController.class,
              route = "students")
public class StudentsManifest extends Manifest<StudentsController> {

    private ArticleRepository articleRepository;
    private StudentServiceRepository studentServiceRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.studentServiceRepository = repositories
                .of(StudentServiceRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public StudentServiceRepository getStudentServiceRepository() {
        return this.studentServiceRepository;
    }
}
