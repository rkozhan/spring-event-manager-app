package eventManager.service;

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
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public Optional<Event> findById(String id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Event update(Event event) {
        return repository.update(event);
    }
}


//TODO ist it working ??