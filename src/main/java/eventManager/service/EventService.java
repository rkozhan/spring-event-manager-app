package eventManager.service;

import eventManager.model.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();

    List<Event> getAllUpcomingEvents();

    Event save(Event event);
    Optional<Event> findById(String id);
    void deleteById(String id);
    Event update(Event event);

}
