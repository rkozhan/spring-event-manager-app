package eventManager.api.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email, password;
}
