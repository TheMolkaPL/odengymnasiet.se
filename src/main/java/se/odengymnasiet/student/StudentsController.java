package se.odengymnasiet.student;

import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsController extends Controller<StudentsManifest> {

    private final StudentServiceRepository studentServiceRepository;

    public StudentsController(Application app,
                              StudentsManifest manifest,
                              Request request,
                              Response response) {
        super(app, manifest, request, response);

        this.studentServiceRepository = manifest.getStudentServiceRepository();
    }

    @HttpRoute("/")
    public Object index() {
        List<StudentService> studentServices = this.studentServiceRepository
                .findAll().stream().collect(Collectors.toList());
        Collections.sort(studentServices);

        Attributes attributes = Attributes.create()
                .add("student_services", studentServices);
        return this.ok("students/index", attributes, "För elever");
    }
}
