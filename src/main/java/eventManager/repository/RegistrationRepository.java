package eventManager.repository;

import eventManager.model.Registration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends MongoRepository<Registration, String> {
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);

    @Query(value = "{ 'eventId' : ?0 }", delete = true)
    void deleteByUserId(String eventId);

    @Query(value = "{ 'userId' : ?0 }", delete = true)
    void deleteByEventId(String userId);
}
