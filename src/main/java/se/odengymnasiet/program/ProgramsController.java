package se.odengymnasiet.program;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.index.Article;
import se.odengymnasiet.index.ArticleRepository;
import se.odengymnasiet.openhouse.OpenHouse;
import se.odengymnasiet.openhouse.OpenHouseRepository;
import se.odengymnasiet.route.HttpRoute;
import se.odengymnasiet.route.RequestMethod;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        Article article = this.articleRepository.findByPath(
                "programs/" + program.getPath() + "/index");
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
                .add("article", article)
                .add("cur_page", "index")
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

        Article article = this.articleRepository.findByPath(
                "programs/" + program.getPath() + "/" + page.toLowerCase());
        if (article == null) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        attributes.add("article", article)
                  .add("cur_page", page.toLowerCase());
        return this.ok("programs/program", attributes, program.getTitle());
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
