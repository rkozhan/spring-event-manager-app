package eventManager.service;
import eventManager.api.response.UserDetailedResponse;
import eventManager.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    User registerUser(User user);
    User findByEmail(String email);
    User findById(String id);
    User update(User user);
    void deleteById(String id);

    UserDetailedResponse getDetailedById(String id);
}
