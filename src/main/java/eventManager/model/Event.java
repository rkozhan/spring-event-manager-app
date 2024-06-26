package eventManager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("events")
public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String category;
    private String createdBy;
    private Set<String> imgUrls;

    public Event(String title, String description, LocalDate date, LocalTime time, String location, String category, String createdBy) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.category = category;
        this.createdBy = createdBy;
    }
}
