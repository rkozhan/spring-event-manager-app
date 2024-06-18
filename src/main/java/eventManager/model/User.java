package eventManager.model;

import eventManager.resource.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private Set<UserRole> roles;

    public User(String username, String password, String email, Set<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
