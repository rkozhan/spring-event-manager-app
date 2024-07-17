package eventManager.service;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository repository;
    @Override
    public Registration create(Registration registration) {
        return repository.save(registration);
    }

    @Override
    public void deleteById(String id) {
        Registration registration = this.findById(id);
        repository.delete(registration);
    }

    @Override
    public void deleteByEventIdAndUserId(String eventId, String userId) {

        Registration registration = this.findByEventIdAndUserId(eventId, userId);
        repository.delete(registration);
    }

    @Override
    public Registration findById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration with id " + id + " not found"));
    }

    @Override
    public Registration findByEventIdAndUserId(String eventId, String userId) {
        return repository.findByEventIdAndUserId(eventId, userId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Registration with eventId " + eventId + " and userId " +userId  + " not found"
                ));
    }

    @Override
    public List<Registration> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Registration> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Registration> findByEventId(String eventId) {
        return repository.findByEventId(eventId);
    }

}
