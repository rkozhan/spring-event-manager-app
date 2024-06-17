package eventManager.controller;

import eventManager.model.Event;
import eventManager.repository.EventRepository;
import eventManager.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Allow this origin
public class EventController {
    private final EventService service;

    @GetMapping()
    public ResponseEntity<List<Event>> findAllEvents () {

        return ResponseEntity.ok(this.service.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Event> saveEvent(@RequestBody Event event) {
        return ResponseEntity.ok(this.service.save(event));
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable String id) {

        Optional<Event> event = this.service.findById(id);

        if(event.isPresent()) {
            return ResponseEntity.ok(event.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The event with ID: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEventById(@PathVariable String id) {

        Optional<Event> event = this.service.findById(id);

        if(event.isPresent()) {
            this.service.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The event with id: " + id + " was not found.");
        }
    }

    //TODO update
}
