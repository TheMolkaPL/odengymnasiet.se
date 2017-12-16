package se.odengymnasiet.contact;

import se.odengymnasiet.Manifest;
import se.odengymnasiet.ManifestInfo;
import se.odengymnasiet.RepositoryContainer;
import se.odengymnasiet.article.ArticleRepository;
import se.odengymnasiet.index.IndexManifest;

@ManifestInfo(name = "Contact",
              parent = IndexManifest.class,
              master = ContactController.class,
              route = "/contact")
public class ContactManifest extends Manifest<ContactController> {

    private ArticleRepository articleRepository;
    private GroupRepository groupRepository;
    private PersonRepository personRepository;

    @Override
    public void installRepositories(RepositoryContainer repositories) {
        this.articleRepository = repositories.of(ArticleRepository.class);
        this.groupRepository = repositories.of(GroupRepository.class);
        this.personRepository = repositories.of(PersonRepository.class);
    }

    public ArticleRepository getArticleRepository() {
        return this.articleRepository;
    }

    public GroupRepository getGroupRepository() {
        return this.groupRepository;
    }

    public PersonRepository getPersonRepository() {
        return this.personRepository;
    }
}
