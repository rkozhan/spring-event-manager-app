package eventManager.service;

import eventManager.api.response.EventDetailedResponce;
import eventManager.api.response.UserDetailedResponse;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.EventRepository;
import eventManager.repository.RegistrationRepository;
import eventManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class EventServiceImpl implements EventService {
    private final EventRepository repository;
    private final UserRepository userRepository;
    private final RegistrationRepository registrationRepository;
    //@Autowired
    //private RegistrationServiceImpl registrationService;  TODO del??

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
    public Event findById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with id " + id + " not found"));
    }

    @Override
    public void deleteById(String id) {
        Event event = this.findById(id);
        repository.delete(event);
        registrationRepository.deleteByEventId(id);
    }

    @Override
    public Event update(Event event) {
        Event eventToUpdate = this.findById(event.getId());

        eventToUpdate.setTitle(event.getTitle());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setDate(event.getDate());
        eventToUpdate.setTime(event.getTime());
        eventToUpdate.setLocation(event.getLocation());
        eventToUpdate.setCategory(event.getCategory());
        eventToUpdate.setImgUrls(event.getImgUrls());
        eventToUpdate.setCancelled(event.isCancelled());

        eventToUpdate.setCreatedBy(event.getCreatedBy());

        return repository.save(eventToUpdate);
    }

    @Override
    public Event toogleIsCancelled(String eventId) {
        Event event = this.findById(eventId);
        event.setCancelled(!event.isCancelled());
        return repository.save(event);
    }

    @Override
    public EventDetailedResponce getDetailedById(String id) {
        EventDetailedResponce eventDetailedResponce = new EventDetailedResponce(this.findById(id));

        List<String> participantsIds = registrationRepository.findByEventId(id)
                .stream().map(Registration::getUserId).toList();
        List<User> participants = new ArrayList<>();

        participantsIds.forEach(userId -> {
            Optional<User> user = userRepository.findById(userId);
            user.ifPresent(participants::add);
        });

        eventDetailedResponce.setParticipants(participants);

        return eventDetailedResponce;
    }
}
