package eventManager.repository;

import eventManager.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);

}
