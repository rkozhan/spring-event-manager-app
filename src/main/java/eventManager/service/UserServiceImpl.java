package eventManager.service;
import eventManager.model.User;
import eventManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl  implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }
    @Override
    public User registerUser(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return null; //TODO update
        //return repository.save(user);
    }
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
