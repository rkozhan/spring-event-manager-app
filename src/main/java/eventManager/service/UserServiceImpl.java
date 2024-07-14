package eventManager.service;
import eventManager.api.response.UserDetailedResponse;
import eventManager.experiments.TestDetailedEntity;
import eventManager.experiments.TestEntity;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.EventRepository;
import eventManager.repository.RegistrationRepository;
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
    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
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
    public User findById(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found"));
    }
    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found"));
    }

    @Override
    public User update(User user) {
        User userToUpdate = this.findById(user.getId());

        String newEmail = user.getEmail();
        if (!userToUpdate.getEmail().equals(newEmail)) {
            if (repository.findByEmail(newEmail).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "An entity with the same email already exists");
            }
        }

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
            //TODO other fields...?
        return repository.save(userToUpdate);
    }

    @Override
    public void deleteById(String id) {
        User user = this.findById(id);
        repository.delete(user);
        registrationRepository.deleteByUserId(id);
    }

    @Override
    public UserDetailedResponse getDetailedById(String id) {
        UserDetailedResponse userDetailedResponse = new UserDetailedResponse(this.findById(id));
        List<Registration> userRegistrations = registrationRepository.findByUserId(id);
        userDetailedResponse.setUserRegistrations(userRegistrations);

        List<String> joinedEventsIds = registrationRepository.findByUserId(id)
                .stream().map(Registration::getEventId).toList();
        List<Event> joinedEvents = new ArrayList<>();

        joinedEventsIds.forEach(eventId -> {
            Optional<Event> event = eventRepository.findById(eventId);
            event.ifPresent(joinedEvents::add);
        });
        userDetailedResponse.setJoinedEvents(joinedEvents);

        userDetailedResponse.setCreatedEvents(eventRepository.findByCreatedBy(id));
        return userDetailedResponse;
    }


}
