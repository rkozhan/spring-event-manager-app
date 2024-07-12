package eventManager.experiments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tests")
public class TestEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;
    private String companyName;

    private LocalDate date;
    private LocalTime time;

    private LocalDateTime createdIn;
    private LocalDateTime updatedIn;

    private String createdById;

    private List<String> imgUrls;
    private String imgUrl;

    private Boolean isCancelled;

    public TestEntity(String email,
                      String companyName,
                      LocalDate date,
                      LocalTime time,
                      String createdById) {
        setEmail(email);
        setCompanyName(companyName);
        setDate(date);
        setTime(time);
        setCreatedById(createdById);
        isCancelled = false;
    }
}
