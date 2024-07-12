package eventManager.experiments;

import eventManager.model.Event;
import eventManager.model.Registration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDetailedEntity extends TestEntity {
    private List<Registration> userRegistrations = new ArrayList<>();
    private List<Event> joinedEvents = new ArrayList<>();
    private List<Event> createdEvents = new ArrayList<>();

    public TestDetailedEntity(TestEntity testEntity) {
        super(testEntity.getId(),
                testEntity.getEmail(),
                testEntity.getCompanyName(),
                testEntity.getDate(),
                testEntity.getTime(),
                testEntity.getCreatedIn(),
                testEntity.getUpdatedIn(),
                testEntity.getCreatedById(),
                testEntity.getImgUrls(),
                testEntity.getImgUrl(),
                testEntity.getIsCancelled()
        );
    }
    public void setUserRegistrations(List<Registration> userRegistrations) {
        this.userRegistrations = userRegistrations;
    }

    public void setJoinedEvents(List<Event> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }
}
