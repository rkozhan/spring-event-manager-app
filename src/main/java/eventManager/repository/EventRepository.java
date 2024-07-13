package eventManager.repository;

import eventManager.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByOrderByDateAscTimeAsc();

}
