package eventManager.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
}
