package clear.solutions.userapp.dto.request;

import clear.solutions.userapp.validation.Email;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserRequestDto {
    @Email
    private String email;
    @NotBlank
    @Size(min = 2)
    private String firstName;
    @NotBlank
    @Size(min = 2)
    private String lastName;
    @NotNull
    @Past
    private LocalDate birthDate;
    @NotBlank
    private String address;
}
