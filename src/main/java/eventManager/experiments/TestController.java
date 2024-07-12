package eventManager.experiments;

import eventManager.config.JwtProvider;
import eventManager.config.JwtTokenValidator;
import eventManager.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    @GetMapping()
    public String permitAll(HttpServletRequest request) {
        // Log request details
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("TEST controller: User: " + authentication.getName());
            return authentication.getAuthorities().toString();
        }
        return "not ok";


        //return authorizationHeader;
    }

    @GetMapping("/haa")
    @PreAuthorize("hasAnyAuthority('EDITOR')")
    public String  ifAuthEditor() {
        return "ok";
    }

    @GetMapping("/haa_r")
    @PreAuthorize("hasAnyAuthority('ROLE_EDITOR')")
    public String  ifAuth_Editor() {
        return "ok";
    }

    @GetMapping("/har")
    @PreAuthorize("hasAnyRole('EDITOR')")
    public String  ifRoleEditor() {
        return "ok";
    }

    @GetMapping("/har_r")
    @PreAuthorize("hasAnyRole('ROLE_EDITOR')")
    public String  ifRole_Editor() {
        return "ok";
    }

    @GetMapping("/a")
    @PreAuthorize("isAuthenticated()")
    public String  ifAuth () {
        return "ok";
    }

}
