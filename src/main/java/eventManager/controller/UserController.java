package eventManager.controller;

import eventManager.model.User;
import eventManager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserRepository repo;

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers () {
        return ResponseEntity.ok(this.repo.findAll());
    }

    //@PostMapping("/save")
    //public ResponseEntity<User> saveUser(@RequestBody User user) {
    //    return ResponseEntity.ok(this.repo.save(user));
    //}

    @PostMapping("/save")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        // Check if the email already exists
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(this.repo.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {

        Optional<User> user = this.repo.findById(id);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.ok("The user with id: " + id + " was not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id) {

        Optional<User> user = this.repo.findById(id);

        if(user.isPresent()) {
            this.repo.deleteById(id);
            return ResponseEntity.ok("Success.");
        } else {
            return ResponseEntity.ok("The user with id: " + id + " was not found.");
        }
    }
}
