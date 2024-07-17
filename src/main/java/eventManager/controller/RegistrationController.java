package eventManager.controller;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.RegistrationRepository;
import eventManager.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/registrations")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService service;

    @GetMapping()
    public ResponseEntity<List<Registration>> findAll () {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping()
    public ResponseEntity<Registration> saveRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(service.create(registration));

//TODO userId + EventId mast be unique
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistrationById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable String id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok("The registration with id: " + id + " deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("/{eventId}/{userId}")
    public ResponseEntity<String> deleteRegistrationById(@PathVariable String eventId, @PathVariable String userId) {
        try {
            service.deleteByEventIdAndUserId(eventId, userId);
            return ResponseEntity.ok("The registration with  eventId " + eventId + " and userId " +userId  + " deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Registration>> findAllRegistrationsByUser(@PathVariable String userId) {
        List<Registration> registrations = service.findByUserId(userId);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<Registration>> findAllRegistrationsByEvent(@PathVariable String eventId) {
        List<Registration> registrations = service.findByEventId(eventId);
        return ResponseEntity.ok(registrations);
    }
}
