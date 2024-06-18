package eventManager.repository;

import eventManager.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);
    Optional<Registration> findById(String id);
    void deleteById (String id);
}
