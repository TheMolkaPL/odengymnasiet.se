package se.odengymnasiet.controller;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Program;
import se.odengymnasiet.route.Route;
import spark.Request;
import spark.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProgramsController extends Controller {
    private final Map<String, Program> programs = new LinkedHashMap<>();

    public ProgramsController(Application application,
                              Request request,
                              Response response) {
        super(application, request, response);

        Program a = new Program();
        a.setTitle("Samhällsvetenskapsprogrammet");
        a.setSubtitle("högskoleförberedande");
        a.setPath("samhallsvetenskap");
        a.setIcon("https://odengymnasiet.se/wp-content/uploads/sam_program.jpg");
        this.programs.put(a.getPath(), a);

        Program b = new Program();
        b.setTitle("Teknikprogrammet");
        b.setSubtitle("högskoleförberedande");
        b.setPath("teknik");
        b.setIcon("https://odengymnasiet.se/wp-content/uploads/te_program.jpg");
        this.programs.put(b.getPath(), b);

        Program c = new Program();
        c.setTitle("Hantverksprogrammet Frisör");
        c.setPath("frisor");
        c.setIcon("https://odengymnasiet.se/wp-content/uploads/frisor_program.jpg");
        this.programs.put(c.getPath(), c);

        Program d = new Program();
        d.setTitle("Hantverksprogrammet Stylist");
        d.setPath("stylist");
        d.setIcon("https://odengymnasiet.se/wp-content/uploads/stylist_program.jpg");
        this.programs.put(d.getPath(), d);

        Program e = new Program();
        e.setTitle("Introduktionsprogrammet");
        e.setPath("introduktion");
        e.setIcon("https://odengymnasiet.se/wp-content/uploads/im_program.jpg");
        this.programs.put(e.getPath(), e);
    }

    @Route("/")
    public Object index() {
        Attributes attributes = Attributes.create()
                .add("programs", this.programs.values());

        return this.ok("programs/index", attributes, "Våra utbildningar");
    }

    @Route("/:program")
    public Object program() {
        String query = this.getRequest().params(":program").toLowerCase();
        if (!this.programs.containsKey(query)) {
            this.getResponse().status(404);
            return this.getResponse().body();
        }

        Program program = this.programs.get(query);

        Attributes attributes = Attributes.create()
                .add("program", program);
        return this.ok("programs/program", attributes, program.getTitle());
    }
}
