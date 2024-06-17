package eventManager.service;

import eventManager.model.Registration;

import java.util.List;

public interface RegistrationService {
    List<Registration> findAll();
    List<Registration> findByUserId(String userId);
    List<Registration> findByEventId(String eventId);
}
