package eventManager.controller;

import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.RegistrationRepository;
import eventManager.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseEntity<Registration> saveRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(service.create(registration));

//TODO userId + EventId mast be unique
    }

    @GetMapping("/{id}")
    public ResponseEntity getRegistrationById(@PathVariable String id) {

        Optional<Registration> registration = service.findById(id);

        if(registration.isPresent()) {
            return ResponseEntity.ok(registration.get());
        } else {
            return ResponseEntity.ok("The registration with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRegistrationById(@PathVariable String id) {

        Optional<Registration> registration = service.findById(id);

        if(registration.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The registration with id: " + id + " was not found.");
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
