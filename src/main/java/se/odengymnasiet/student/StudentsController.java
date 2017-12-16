package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsController extends Controller<StudentsManifest> {

    private final ArticleRepository articleRepository;
    private final StudentServiceRepository studentServiceRepository;

    public StudentsController(Application app,
                              StudentsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);

        this.articleRepository = manifest.getArticleRepository();
        this.studentServiceRepository = manifest.getStudentServiceRepository();
    }

    @HttpRoute("/")
    public Object index() {
        Article article = this.articleRepository
                .findByPath(ArticlePaths.students() + "/");
        if (article == null) {
            article = Article.NULL;
        }

        List<StudentService> studentServices = this.studentServiceRepository
                .findAll().stream().collect(Collectors.toList());
        Collections.sort(studentServices);

        Attributes attributes = Attributes.create()
                .add("article", article)
                .add("student_services", studentServices);
        return this.ok("students/index", attributes, "För elever");
    }
}
