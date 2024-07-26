package eventManager.api.response;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailedResponse extends User {
    private List<Registration> userRegistrations = new ArrayList<>();
    private List<Event> joinedEvents = new ArrayList<>();
    private List<Event> createdEvents = new ArrayList<>();

    public UserDetailedResponse(User user) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
    }
}
