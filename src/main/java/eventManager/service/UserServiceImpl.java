package eventManager.service;
import eventManager.api.response.UserDetailedResponse;
import eventManager.experiments.TestDetailedEntity;
import eventManager.experiments.TestEntity;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl  implements UserService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private RegistrationServiceImpl registrationService;

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
    public User findById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found"));
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User update(User user) {
        Optional<User> userToUpdate = repository.findById(user.getId());
        if (userToUpdate.isPresent()) {
            userToUpdate.get().setUsername(user.getUsername());
            //TODO other fields. email ?
            return repository.save(userToUpdate.get());
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + user.getId() + " not found");
        }
    }
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetailedResponse getDetailedById(String id) {
        UserDetailedResponse userDetailedResponse = new UserDetailedResponse(this.findById(id));

        List<Registration> userRegistrations = registrationService.findByUserId(id);
        userDetailedResponse.setUserRegistrations(userRegistrations);

        return userDetailedResponse;
    }


}
