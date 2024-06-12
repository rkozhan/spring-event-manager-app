package eventManager.controller;

import eventManager.model.Event;
import eventManager.model.Student;
import eventManager.repository.EventRepository;
import eventManager.resource.StudentRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventRepository repo;

    @GetMapping()
    public ResponseEntity<List<Event>> findAllEvents () {

        return ResponseEntity.ok(this.repo.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Event> saveEvent(@RequestBody Event event) {
        return ResponseEntity.ok(this.repo.save(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable String id) {

        Optional<Event> event = this.repo.findById(id);

        if(event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.ok("The event with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEventById(@PathVariable String id) {

        Optional<Event> event = this.repo.findById(id);

        if(event.isPresent()) {
            this.repo.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The event with id: " + id + " was not found.");
        }
    }
}
