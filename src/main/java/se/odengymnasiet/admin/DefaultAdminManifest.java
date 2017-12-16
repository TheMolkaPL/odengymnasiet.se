package se.odengymnasiet.admin;

import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.index.IndexManifest;
import se.odengymnasiet.user.UserRepository;

@ManifestInfo(name = "Admin",
              parent = IndexManifest.class,
              master = DefaultAdminController.class,
              route = "/admin")
public class DefaultAdminManifest
        extends AdminManifest<DefaultAdminController> {

    private UserRepository userRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.userRepository = repositories.of(UserRepository.class);
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }
}
