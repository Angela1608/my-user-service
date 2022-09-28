package user.service.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;
import user.service.validation.Email;

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
