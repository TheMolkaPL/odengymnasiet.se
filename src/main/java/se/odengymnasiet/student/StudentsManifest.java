package se.odengymnasiet.student;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Students",
              parent = IndexManifest.class,
              master = StudentsController.class,
              route = "students")
public class StudentsManifest extends Manifest<StudentsController> {
}
