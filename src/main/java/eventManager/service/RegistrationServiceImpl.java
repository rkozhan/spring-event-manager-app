package eventManager.service;

import eventManager.model.Registration;
import eventManager.repository.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        repository.deleteById(id);
    }

    @Override
    public Optional<Registration> findById(String id) {
        return repository.findById(id);
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
