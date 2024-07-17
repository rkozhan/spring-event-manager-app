package eventManager.service;
import eventManager.api.response.EventDetailedResponce;
import eventManager.api.response.UserDetailedResponse;
import eventManager.model.Event;
import java.util.List;
import java.util.Optional;
public interface EventService {
    List<Event> findAll();

    List<Event> getAllUpcomingEvents();

    Event save(Event event);

    Event findById(String id);

    Event update(Event event);

    Event toogleIsCancelled(String eventId);

    void deleteById(String id);

    EventDetailedResponce getDetailedById(String id);

    EventDetailedResponce getRandomDetailedEvent();
}
