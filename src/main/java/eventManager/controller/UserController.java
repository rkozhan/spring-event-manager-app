package eventManager.controller;

import eventManager.model.User;
import eventManager.repository.UserRepository;
import eventManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers () {
        return ResponseEntity.ok(service.findAllUsers());
    }

    //@PostMapping("/save")
    //public ResponseEntity<User> saveUser(@RequestBody User user) {
    //    return ResponseEntity.ok(this.repo.save(user));
    //}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if the email already exists
        if (service.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(service.registerUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {

        Optional<User> user = service.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.ok("The user with id: " + id + " was not found.");
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {

        Optional<User> user = service.findByEmail(email);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.ok("The user with id: " + email + " was not found.");
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
}
