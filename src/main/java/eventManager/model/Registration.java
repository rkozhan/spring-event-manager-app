package eventManager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document("registrations")
@CompoundIndexes({
        @CompoundIndex(name = "user_event_idx", def = "{'userId': 1, 'eventId': 1}", unique = true)
})
public class Registration {
    @Id
    private String id;
    @NonNull
    private String userId;
    @NonNull
    private String eventId;
}
