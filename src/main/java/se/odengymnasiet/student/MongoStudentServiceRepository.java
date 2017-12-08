package se.odengymnasiet.student;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import se.odengymnasiet.RepositoryHandler;
import se.odengymnasiet.mongo.MongoCollectionName;
import se.odengymnasiet.mongo.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

@MongoCollectionName("student_services")
@RepositoryHandler(StudentServiceRepository.class)
public class MongoStudentServiceRepository
        extends MongoRepository<StudentService>
        implements StudentServiceRepository {

    public MongoStudentServiceRepository(MongoDatabase database,
                                         MongoCollection<Document> collection) {
        super(database, collection);
    }

    @Override
    public StudentService deserialize(Document data) {
        return StudentService.deserialize(data);
    }

    @Override
    public Collection<StudentService> findNavbar() {
        return this.getCollection().find(eq(StudentService.FIELD_NAVBAR, true))
                .map(this::deserialize)
                .into(new ArrayList<>());
    }
}
