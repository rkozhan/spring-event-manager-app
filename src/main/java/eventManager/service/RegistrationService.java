package eventManager.service;

import eventManager.model.Registration;
import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration create(Registration registration);
    void deleteById(String id);

    void deleteByEventIdAndUserId(String eventId, String userId);
    Registration findById(String id);

    Registration findByEventIdAndUserId(String eventId, String userId);
    List<Registration> findAll();
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);
}
