package clear.solutions.userapp.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
}
