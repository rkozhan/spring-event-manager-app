package eventManager.service;
import eventManager.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    User registerUser(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    User updateUser(User user);
    void deleteById(String id);
}
