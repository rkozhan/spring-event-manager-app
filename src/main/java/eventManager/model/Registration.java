package eventManager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("registrations")
@CompoundIndexes({
        @CompoundIndex(name = "user_event_idx", def = "{'userId': 1, 'eventId': 1}", unique = true)
})
public class Registration {
    @Id
    private String id;
    private String userId;
    private String eventId;

    public Registration(String userId, String eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
}
