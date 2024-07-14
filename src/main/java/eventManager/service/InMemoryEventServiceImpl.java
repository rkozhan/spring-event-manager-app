package eventManager.service;

import eventManager.api.response.EventDetailedResponce;
import eventManager.model.Event;
import eventManager.repository.InMemoryEventDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InMemoryEventServiceImpl implements EventService {
    private final InMemoryEventDAO repository;
    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Event> getAllUpcomingEvents() {
        return null;
    }

    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public Event findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public EventDetailedResponce getDetailedById(String id) {
        return null;
    }

    @Override
    public Event update(Event event) {
        return repository.update(event);
    }

    @Override
    public Event toogleIsCancelled(String eventId) {
        return null;
    }
}


//TODO check if it works