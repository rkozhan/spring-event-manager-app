package eventManager.controller;

import eventManager.api.request.LoginRequest;
import eventManager.api.request.UserRequest;
import eventManager.api.response.AuthResponse;
import eventManager.config.JwtProvider;
import eventManager.model.User;
import eventManager.resource.UserRole;
import eventManager.service.MyUserDetailsServiceImpl;
import eventManager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private MyUserDetailsServiceImpl myUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUserHandler (@RequestBody UserRequest request) {

        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setRoles(request.getRoles());
            User registeredUser = userService.registerUser(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generateToken(authentication);

            AuthResponse resp = new AuthResponse();
            resp.setMessage("signup success");
            resp.setJwt(jwt);

            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // New endpoint for adding admins
    @PostMapping("/admin")
    public ResponseEntity<?> addAdmin(@RequestBody UserRequest request) {
        try {
            // Validate and create user with ROLE_ADMIN
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setRoles(Set.of(UserRole.ROLE_ADMIN)); // Assign ROLE_ADMIN explicitly

            User registeredUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse resp = new AuthResponse();
        resp.setMessage("login success");
        resp.setJwt(jwt);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
