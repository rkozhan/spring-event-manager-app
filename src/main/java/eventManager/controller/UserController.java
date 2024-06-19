package eventManager.controller;

import eventManager.api.UserRequest;
import eventManager.model.User;
import eventManager.repository.UserRepository;
import eventManager.resource.UserRole;
import eventManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<User>> findAllUsers () {
        return ResponseEntity.ok(service.findAllUsers());
    }


    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        // Validate roles in request to prevent admin role assignment
        if (request.getRoles() != null && request.getRoles().contains(UserRole.ROLE_ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Creating admins is not allowed.");
        }
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setRoles(request.getRoles());
            User registeredUser = service.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // New endpoint for adding admins
    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addAdmin(@RequestBody UserRequest request) {
        try {
            // Validate and create user with ROLE_ADMIN
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setRoles(Set.of(UserRole.ROLE_ADMIN)); // Assign ROLE_ADMIN explicitly

            User registeredUser = service.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity getUserById(@PathVariable String id) {

        Optional<User> user = service.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The user with ID: " + id + " was not found.");
        }
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity getUserByEmail(@PathVariable String email) {

        Optional<User> user = service.findByEmail(email);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The user with email: " + email + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id) {

        Optional<User> user = service.findById(id);

        if(user.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The user with id: " + id + " was not found.");
        }
    }

    //TODO update
}
