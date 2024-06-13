package eventManager.controller;

import eventManager.model.Registration;
import eventManager.repository.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/registrations")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationRepository repo;

    @PostMapping("/save")
    public ResponseEntity<Registration> saveRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(this.repo.save(registration));

//TODO userId + EventId mast be unique
    }

    @GetMapping("/{id}")
    public ResponseEntity getRegistrationById(@PathVariable String id) {

        Optional<Registration> registration = this.repo.findById(id);

        if(registration.isPresent()) {
            return ResponseEntity.ok(registration.get());
        } else {
            return ResponseEntity.ok("The registration with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRegistrationById(@PathVariable String id) {

        Optional<Registration> registration = this.repo.findById(id);

        if(registration.isPresent()) {
            this.repo.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The registration with id: " + id + " was not found.");
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Registration>> findAllRegistrationsByUser(@PathVariable String userId) {
        List<Registration> registrations = repo.findByUserId(userId);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<Registration>> findAllRegistrationsByEvent(@PathVariable String eventId) {
        List<Registration> registrations = repo.findByEventId(eventId);
        return ResponseEntity.ok(registrations);
    }
}
