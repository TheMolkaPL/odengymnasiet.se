package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.article.NavigationItem;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

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
        // article
        String articlePath = ArticlePaths.students() + "/";
        Article article = this.articleRepository.findByPath(articlePath);
        if (article == null) {
            article = Article.NULL;
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                this.articleRepository,
                articlePath,
                articlePath,
                "För elever");

        // student services
        List<StudentService> studentServices = new ArrayList<>(
                this.studentServiceRepository.findAll());

        Attributes attributes = Attributes.create()
                .add("article", article)
                .add("pages", pages)
                .add("studentServices", studentServices);
        return this.ok("students/index", attributes, article.getTitle());
    }

    @HttpRoute("/:page")
    public Object page() {
        String path = this.getRequest().params(":page");

        // article
        String articlePath = ArticlePaths.students(path.toLowerCase());
        Article article = this.articleRepository.findByPath(articlePath);
        if (article == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // pages
        List<NavigationItem> pages = NavigationItem.list(
                this.articleRepository,
                ArticlePaths.students() + "/",
                articlePath,
                "För elever");

        // student services
        List<StudentService> studentServices = new ArrayList<>(
                this.studentServiceRepository.findAll());

        Attributes attributes = Attributes.create()
                .add("article", article)
                .add("pages", pages)
                .add("studentServices", studentServices);
        return this.ok("students/index", attributes, article.getTitle());
    }
}
