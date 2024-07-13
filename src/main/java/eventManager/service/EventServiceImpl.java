package eventManager.service;

import eventManager.model.Event;
import eventManager.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    @Override
    public List<Event> findAll() {
        return repository.findAllByOrderByDateAscTimeAsc();
    }

    public List<Event> getAllUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();

        // Fetch all events sorted by date and time
        List<Event> allEvents = repository.findAllByOrderByDateAscTimeAsc();

        // Filter out past events
        return allEvents.stream()
                .filter(event -> event.getDate().atTime(event.getTime()).isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public Optional<Event> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Event update(Event event) {
        return null;
    }
}
