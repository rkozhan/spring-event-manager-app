package eventManager.repository;
import eventManager.model.Event;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryEventDAO {
    private final List<Event> EVENTS = new ArrayList<>();
    //TODO @PostConstruct private void loadEventsFromDB to EVENTS

    public List<Event> findAll() {
        return EVENTS;
    }

    public Event save(Event student) {
        EVENTS.add(student);
        return student;
    }

    public Event findById(String id) {
        return EVENTS.stream()
                .filter(element -> element.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(String id) {
        var event = findById(id);
        if (event != null) {
            EVENTS.remove(event);
        }

    }

    public Event update(Event event) {
        var eventIndex = IntStream.range(0, EVENTS.size())
                .filter(index -> EVENTS.get(index).getId().equals(event.getId()))
                .findFirst()
                .orElse(-1);
        if (eventIndex > -1) {
            EVENTS.set(eventIndex, event);
            return event;
        }
        return null;
    }
}
