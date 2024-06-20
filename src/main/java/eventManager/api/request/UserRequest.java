package eventManager.api.request;

import eventManager.resource.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Set<UserRole> roles;

    public UserRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles.add(UserRole.ROLE_USER); // Add ROLE_USER as default role
    }
}
