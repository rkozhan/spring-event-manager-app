package eventManager.controller;

import eventManager.api.response.UserDetailedResponse;
import eventManager.model.User;
import eventManager.service.UserService;
import eventManager.config.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private JwtProvider jwtProvider;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void findAllUsers() throws Exception {
        User user = new User("1", "username", "password", "email@example.com", null);
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("username"));
    }

    @Test
    public void getUserById() throws Exception {
        String userId = "1";
        User user = new User(userId, "username", "password", "email@example.com", null);
        when(userService.findById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void getUserById_NotFound() throws Exception {
        String userId = "1";
        when(userService.findById(userId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User with id " + userId + " not found"));
    }

    @Test
    public void getUserByEmail() throws Exception {
        String email = "email@example.com";
        User user = new User("1", "username", "password", email, null);
        when(userService.findByEmail(email)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }


    @Test
    public void deleteUserById_Success() throws Exception {
        String userId = "1";
        doNothing().when(userService).deleteById(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The user with id: " + userId + " deleted successfully"));
    }

    @Test
    public void deleteUserById_NotFound() throws Exception {
        String userId = "1";
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found")).when(userService).deleteById(userId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User with id " + userId + " not found"));
    }

    @Test
    public void updateUser_Success() throws Exception {
        String userId = "1";
        User user = new User(userId, "username", "password", "email@example.com", null);
        when(userService.update(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users")
                        .contentType("application/json")
                        .content("{\"id\": \"" + userId + "\", \"username\": \"username\", \"password\": \"password\", \"email\": \"email@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void updateUser_NotFound() throws Exception {
        String userId = "1";
        when(userService.update(any(User.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users")
                        .contentType("application/json")
                        .content("{\"id\": \"" + userId + "\", \"username\": \"username\", \"password\": \"password\", \"email\": \"email@example.com\"}"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("User not found"));
    }

    @Test
    public void testGetDetailedById() throws Exception {
        String userId = "1";
        UserDetailedResponse response = new UserDetailedResponse(new User(userId, "username", "password", "email@example.com", null));
        when(userService.getDetailedById(userId)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/detailed/{id}", userId))
                .andExpect(status().isOk());
    }
}