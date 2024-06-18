package eventManager.service;

import eventManager.model.Registration;
import eventManager.model.User;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration create(Registration registration);
    void deleteById(String id);
    Optional<Registration> findById(String id);
    List<Registration> findAll();
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);
}
