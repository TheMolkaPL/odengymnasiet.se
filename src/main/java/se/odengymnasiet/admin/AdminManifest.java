package se.odengymnasiet.admin;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Admin",
              parent = IndexManifest.class,
              master = AdminController.class,
              route = "/admin")
public class AdminManifest extends Manifest<AdminController> {
}
