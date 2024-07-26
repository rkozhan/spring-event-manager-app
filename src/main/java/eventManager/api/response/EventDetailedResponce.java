package eventManager.api.response;

import eventManager.model.Event;
import eventManager.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailedResponce extends Event {
    private List<User> participants = new ArrayList<>();

    public EventDetailedResponce (Event event) {
        super(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getDate(),
            event.getTime(),
            event.getLocation(),
            event.getCategory(),
            event.getCreatedBy(),
            event.getImgUrls(),
            event.isCancelled()
        );
    }
}