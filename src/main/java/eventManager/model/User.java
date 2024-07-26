package eventManager.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import eventManager.resource.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    @NonNull
    private String username;
    @JsonIgnore
    @NonNull
    private String password;
    @NonNull
    private String email;
    @JsonIgnore
    private Set<UserRole> roles;
}
