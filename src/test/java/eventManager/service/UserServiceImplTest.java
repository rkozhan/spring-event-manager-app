package eventManager.service;

import eventManager.api.response.UserDetailedResponse;
import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.model.User;
import eventManager.repository.EventRepository;
import eventManager.repository.RegistrationRepository;
import eventManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllUsers() {
        List<User> users = Arrays.asList(new User("username1", "password1", "email1@example.com"),
                new User("username2", "password2", "email2@example.com"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void registerUser() {
        User user = new User("username", "password", "email@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("username", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("email@example.com", result.getEmail());
        verify(userRepository, times(1)).findByEmail("email@example.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void registerUser_EmailAlreadyExists() {
        User user = new User("username", "password", "email@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
        verify(userRepository, times(1)).findByEmail("email@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findById() {
        User user = new User("username", "password", "email@example.com");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        User result = userService.findById("1");

        assertEquals("username", result.getUsername());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void findById_NotFound() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(ResponseStatusException.class, () -> userService.findById("1"));

        // Assert
        String expectedMessage = "User with id 1 not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Additional assertions
        assertTrue(exception instanceof ResponseStatusException);

        // Verify interactions
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, never()).delete(any(User.class));
        verify(registrationRepository, never()).deleteByUserId(anyString());
    }


    @Test
    void findByEmail() {
        User user = new User("username", "password", "email@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User result = userService.findByEmail("email@example.com");

        assertEquals("username", result.getUsername());
        verify(userRepository, times(1)).findByEmail("email@example.com");
    }

    @Test
    void findByEmail_NotFound() {
        // Arrange
        String email = "test123@mail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(ResponseStatusException.class, () -> userService.findByEmail(email));

        // Assert
        String expectedMessage = "User with email " + email + " not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Additional assertions
        assertTrue(exception instanceof ResponseStatusException);

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void update() {
        User user = new User("username", "password", "email@example.com");
        user.setId("1");
        User updatedUser = new User("newUsername", "password", "newEmail@example.com");
        updatedUser.setId("1");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(updatedUser);

        assertEquals("newUsername", result.getUsername());
        assertEquals("newEmail@example.com", result.getEmail());
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).findByEmail("newEmail@example.com");
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void update_EmailConflict() {
        User user = new User("username", "password", "email@example.com");
        user.setId("1");
        User updatedUser = new User("newUsername", "password", "newEmail@example.com");
        updatedUser.setId("1");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.update(updatedUser));
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).findByEmail("newEmail@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteById() {
        User user = new User("username", "password", "email@example.com");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        userService.deleteById("1");

        verify(userRepository, times(1)).delete(user);
        verify(registrationRepository, times(1)).deleteByUserId("1");
    }

    @Test
    void deleteById_NotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.deleteById("1"));
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, never()).delete(any(User.class));
        verify(registrationRepository, never()).deleteByUserId(anyString());
    }

    @Test
    void getDetailedById() {
        User user = new User("username", "password", "email@example.com");
        user.setId("1");
        List<Registration> registrations = List.of(
                new Registration("1", "1"),
                new Registration("2", "1")
        );
        List<Event> events = List.of(
                new Event("title1", "description1", LocalDate.now(), LocalTime.now(), "location1", "category1", "1"),
                new Event("title2", "description2", LocalDate.now(), LocalTime.now(), "location2", "category2", "1")
        );

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(registrationRepository.findByUserId(anyString())).thenReturn(registrations);
        when(eventRepository.findById(anyString()))
                .thenReturn(Optional.of(events.get(0)))
                .thenReturn(Optional.of(events.get(1)));
        when(eventRepository.findByCreatedBy(anyString())).thenReturn(events);

        UserDetailedResponse result = userService.getDetailedById("1");

        assertEquals("username", result.getUsername());
        assertEquals(2, result.getJoinedEvents().size());
        assertEquals(2, result.getUserRegistrations().size());
        assertEquals(2, result.getCreatedEvents().size());
        verify(userRepository, times(1)).findById("1");
        verify(registrationRepository, times(2)).findByUserId("1");
        verify(eventRepository, times(2)).findById(anyString());
        verify(eventRepository, times(1)).findByCreatedBy("1");
    }
}