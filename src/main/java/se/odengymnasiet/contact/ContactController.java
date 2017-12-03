package se.odengymnasiet.contact;

import org.bson.types.ObjectId;
import se.odengymnasiet.Application;
import se.odengymnasiet.Attributes;
import se.odengymnasiet.Controller;
import se.odengymnasiet.route.HttpRoute;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactController extends Controller<ContactManifest> {

    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;

    public ContactController(Application app,
                             ContactManifest manifest,
                             Request request,
                             Response response) {
        super(app, manifest, request, response);

        this.groupRepository = manifest.getGroupRepository();
        this.personRepository = manifest.getPersonRepository();
    }

    @HttpRoute("/")
    public Object index() {
        return this.ok("contact/index", Attributes.create(), "Kontakt");
    }

    @HttpRoute("/staff")
    public Object staff() {
        Map<ObjectId, Group> dbGroups = new HashMap<>();
        this.groupRepository.findAll().forEach(group -> {
            dbGroups.put(group.getId(), group);
        });

        Group defaultGroup = new Group() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };

        List<PersonGroupRelation> relations = new ArrayList<>();
        this.personRepository.findContactable().forEach(person -> {
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
            if (this.getName() == null) {
                return Integer.MIN_VALUE;
            } else if (o.getName() == null) {
                return Integer.MAX_VALUE;
            } else {
                return this.getName().compareToIgnoreCase(o.getName());
            }
        }

        public String getName() {
            return this.group.getName();
        }

        public List<PersonGroupRelation> getPersons() {
            return this.persons;
        }
    }

    @HttpRoute("/application")
    public Object application() {
        return this.ok("contact/application", Attributes.create(), "Ansökan");
    }
}
