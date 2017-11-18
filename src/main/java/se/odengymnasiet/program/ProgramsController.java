package se.odengymnasiet.program;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

public class ProgramsController extends Controller {
    private final ProgramRepository programs;

    public ProgramsController(Application application,
                              Request request,
                              Response response) {
        super(application, request, response);

        this.programs = application.getRepository(ProgramRepository.class);
    }

    @Route("/")
    public Object index() {
        Attributes attributes = Attributes.create()
                .add("programs", this.programs.findAll());
        return this.ok("programs/index", attributes, "Våra utbildningar");
    }

    @Route("/:program")
    public Object program() {
        String path = this.getRequest().params(":program");
        Program program = this.programs.findByPath(path);

        if (program != null) {
            Attributes attributes = Attributes.create()
                    .add("program", program);
            return this.ok("programs/program", attributes, program.getTitle());
        }

        this.getResponse().status(404);
        return this.getResponse().body();
    }
}
