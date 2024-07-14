package eventManager.controller;

import eventManager.model.Event;
import eventManager.model.User;
import eventManager.repository.EventRepository;
import eventManager.service.EventService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000") // Allow this origin
public class EventController {
    private final EventService service;

    @GetMapping()
    public ResponseEntity<List<Event>> findAllEvents () {
        return ResponseEntity.ok(service.getAllUpcomingEvents());
    }

    @PostMapping()
    public ResponseEntity<Event> saveEvent(@RequestBody Event event) {
        return ResponseEntity.ok(service.save(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("/{id}")
    //@PostAuthorize("returnObject.createdBy eq authorisation.name") //TODO not working
    //@PostAuthorize("returnObject.createdBy == principal.username")
    public ResponseEntity<String> deleteEventById(@PathVariable String id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok("The event with id: " + id + " deleted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        try {
            return ResponseEntity.ok(service.update(event));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    @GetMapping("/detailed/{id}")
    public ResponseEntity<?> getDetailedById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getDetailedById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
