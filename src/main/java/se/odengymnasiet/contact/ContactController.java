package se.odengymnasiet.contact;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.article.Article;
import se.odengymnasiet.article.ArticlePaths;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactController extends Controller<ContactManifest> {

    public ContactController(Application app,
                             ContactManifest manifest,
                             Request request,
                             Response response) {
        super(app, manifest, request, response);
    }

    @HttpRoute("/")
    public Object index() {
        Article article = this.getManifest().getArticleRepository()
                .findByPath(ArticlePaths.contact());
        if (article == null) {
            article = Article.NULL;
        }

        Attributes attributes = Attributes.create()
                .add("article", article);
        return this.ok("contact/index", attributes, "Kontakt");
    }

    @HttpRoute("/staff")
    public Object staff() {
        ContactManifest manifest = this.getManifest();

        Map<ObjectId, Group> dbGroups = manifest.getGroupRepository()
                .findAll().stream()
                .collect(Collectors.toMap(Group::getId, group -> group));

        Group defaultGroup = new Group() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getRoleName() {
                return "Funktion";
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };

        List<PersonGroupRelation> relations = new ArrayList<>();
        manifest.getPersonRepository().findContactable().forEach(person -> {
            person.getGroups().forEach(personGroup -> {
                Group group = null;
                ObjectId groupId = personGroup.getGroupId();
                if (groupId != null) {
                    group = dbGroups.get(groupId);
                }

                if (group == null) {
                    group = defaultGroup;
                }

                relations.add(new PersonGroupRelation(
                        person, group, personGroup));
            });
        });

        List<GroupView> groups = new ArrayList<>();
        relations.forEach(relation -> {
            Group group = relation.getGroup();
            if (!groups.stream().anyMatch(view -> view.group.equals(group))) {
                groups.add(new GroupView(group));
            }

            GroupView view = groups.stream()
                    .filter(groupView -> groupView.group.equals(group))
                    .findAny().get();
            view.getPersons().add(relation);
        });

        Collections.sort(groups);
        groups.forEach(groupView -> Collections.sort(groupView.getPersons()));

        Attributes attributes = Attributes.create()
                .add("groups", groups);
        return this.ok("contact/staff", attributes, "Personal");
    }

    public class PersonGroupRelation
            implements Comparable<PersonGroupRelation> {

        private final Person person;
        private final Group group;
        private final Person.PersonGroup personGroup;

        private PersonGroupRelation(Person person, Group group,
                                    Person.PersonGroup personGroup) {
            this.person = person;
            this.group = group;
            this.personGroup = personGroup;
        }

        @Override
        public int compareTo(PersonGroupRelation o) {
            return this.getPerson().compareTo(o.getPerson());
        }

        public Person getPerson() {
            return this.person;
        }

        public Group getGroup() {
            return this.group;
        }

        public Person.PersonGroup getPersonGroup() {
            return this.personGroup;
        }
    }

    public class GroupView implements Comparable<GroupView> {

        private final Group group;
        private final List<PersonGroupRelation> persons = new ArrayList<>();

        private GroupView(Group group) {
            this.group = group;
        }

        @Override
        public int compareTo(GroupView o) {
            String thisName = this.getGroup().getName();
            String thatName = o.getGroup().getName();

            if (thisName == null) {
                return Integer.MIN_VALUE;
            } else if (thatName == null) {
                return Integer.MAX_VALUE;
            } else {
                return thisName.compareToIgnoreCase(thatName);
            }
        }

        public Group getGroup() {
            return this.group;
        }

        public List<PersonGroupRelation> getPersons() {
            return this.persons;
        }
    }
}
