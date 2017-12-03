package se.odengymnasiet.program;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramsController extends Controller<ProgramsManifest> {

    private final ProgramRepository programRepository;

    public ProgramsController(Application app,
                              ProgramsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);

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

        if (program != null) {
            Attributes attributes = Attributes.create()
                    .add("program", program);
            return this.ok("programs/program", attributes, program.getTitle());
        }

        this.getResponse().status(404);
        return this.getResponse().body();
    }
}
