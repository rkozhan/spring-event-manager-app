package eventManager.controller;

import eventManager.config.JwtProvider;
import eventManager.model.User;
import eventManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService service, JwtProvider jwtProvider) {
        this.service = service;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers () {
        return ResponseEntity.ok(service.findAllUsers());
    }


    @GetMapping("/{id}")
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
    public ResponseEntity getUserByEmail(@PathVariable String email) {

        Optional<User> user = service.findByEmail(email);

        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The user with email: " + email + " was not found.");
        }
    }


    @GetMapping("/me")
    public ResponseEntity getMe(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }

        String email;
        try {
            email = jwtProvider.getEmailFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid JWT token");
        }

        Optional<User> user = service.findByEmail(email);
        if (user.isPresent()) {
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
