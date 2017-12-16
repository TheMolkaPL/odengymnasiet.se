package se.odengymnasiet.program;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.openhouse.OpenHouse;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.RequestMethod;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramsController extends Controller<ProgramsManifest> {

    private final ArticleRepository articleRepository;
    private final OpenHouseRepository openHouseRepository;
    private final ProgramRepository programRepository;

    public ProgramsController(Application app,
                              ProgramsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);

        this.articleRepository = manifest.getArticleRepository();
        this.openHouseRepository = manifest.getOpenHouseRepository();
        this.programRepository = manifest.getProgramRepository();
    }

    @HttpRoute("/")
    public Object index() {
        List<Program> programs = new ArrayList<>(
                this.programRepository.findAll());
        Collections.sort(programs);

        Attributes attributes = Attributes.create()
                .add("programs", programs);
        return this.ok("programs/index", attributes, "Våra utbildningar");
    }

    @HttpRoute("/:program")
    public Object program() {
        String path = this.getRequest().params(":program");
        Program program = this.programRepository.findByPath(path);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // article
        String articlePath = ArticlePaths.programs(program.getPath()) + "/";
        Article article = this.articleRepository.findByPath(articlePath);
        if (article == null) {
            article = Article.NULL;
        }

        // open houses
        List<OpenHouse> openHouses = new ArrayList<>(
                this.openHouseRepository.findAllDeployedComing());
        openHouses = openHouses.stream()
                .filter(openHouse -> {
                    ObjectId programId = program.getId();
                    return openHouse.getPrograms().contains(programId);
                })
                .collect(Collectors.toList());
        Collections.sort(openHouses);

        Attributes attributes = Attributes.create()
                .add("program", program)
                .add("pages", this.navigationItems(
                        program, articlePath, articlePath))
                .add("article", article)
                .add("openHouses", openHouses);
        return this.ok("programs/program", attributes, program.getTitle());
    }

    @HttpRoute("/:program/:page")
    public Object programPage() {
        String path = this.getRequest().params(":program");
        String page = this.getRequest().params(":page");
        Program program = this.programRepository.findByPath(path);

        Attributes attributes = Attributes.create()
                .add("program", program);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        } else if (page.equalsIgnoreCase("application")) {
            if (program.isOpen()) {
                return this.ok("programs/program_application", attributes,
                        "Anmälan till " + program.getTitle());
            } else {
                return this.ok("programs/program_application_closed",attributes,
                        "Anmälan till " + program.getTitle() + " är stängd");
            }
        }

        // article
        String articlePath = ArticlePaths.programs(program.getPath(),
                                                   page.toLowerCase());
        Article article = this.articleRepository.findByPath(articlePath);
        if (article == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        // open houses
        List<OpenHouse> openHouses = new ArrayList<>(
                this.openHouseRepository.findAllDeployedComing());
        openHouses = openHouses.stream()
                .filter(openHouse -> {
                    ObjectId programId = program.getId();
                    return openHouse.getPrograms().contains(programId);
                })
                .collect(Collectors.toList());
        Collections.sort(openHouses);

        String indexPath = ArticlePaths.programs(program.getPath()) + "/";
        attributes.add("pages", this.navigationItems(
                          program, articlePath, indexPath))
                  .add("article", article)
                  .add("openHouses", openHouses);
        return this.ok("programs/program", attributes, program.getTitle());
    }

    private List<NavigationItem> navigationItems(
            Program program, String now, String indexPath) {
        String path = ArticlePaths.programs(program.getPath()) + "/";
        List<Article> articles = new ArrayList<>(this.articleRepository
                .findAllByStartingPath(path));

        Optional<Article> indexMaybe = articles.stream()
                .filter(article -> article.getPath().equals(path))
                .findFirst();

        Article index;
        if (indexMaybe.isPresent()) {
            index = indexMaybe.get();
        } else {
            articles.add(index = Article.copyOf(Article.NULL));
        }

        index.setPath(path);
        index.setPriority(Integer.MAX_VALUE);
        index.setTitle("Om utbildningen");

        List<NavigationItem> items = new ArrayList<>();
        articles.forEach(article -> {
            String target = article.getPath();
            boolean active = article.getPath().equals(now);

            if (indexPath != null && article.getPath().equals(indexPath)) {
                target = target.substring(0, target.length() - 1);
            }

            items.add(new NavigationItem(article, target, active));
        });

        Collections.sort(items);
        return items;
    }

    public class NavigationItem implements Comparable<NavigationItem> {

        private final Article article;
        private final String target;
        private final boolean active;

        public NavigationItem(Article article, String target) {
            this(article, target, false);
        }

        public NavigationItem(Article article, String target, boolean active) {
            this.article = article;
            this.target = target;
            this.active = active;
        }

        @Override
        public int compareTo(NavigationItem o) {
            return this.getArticle().compareTo(o.getArticle());
        }

        public Article getArticle() {
            return this.article;
        }

        public String getTarget() {
            return this.target;
        }

        public boolean isActive() {
            return this.active;
        }
    }

    @HttpRoute(value = "/:program/application", methods = RequestMethod.POST)
    public Object programApplicationPost() {
        String path = this.getRequest().params(":program");
        Program program = this.programRepository.findByPath(path);

        Attributes attributes = Attributes.create()
                .add("program", program);

        if (program == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        } else if (!program.isOpen()) {
            return this.ok("programs/program_application_closed", attributes,
                    "Anmälan till " + program.getTitle() + " är stängd");
        }

        // TODO Validate the form

        return this.ok("programs/program_application_ok", attributes,
                "Tack för din anmälan till " + program.getTitle());
    }
}
