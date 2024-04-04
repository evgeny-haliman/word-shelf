package word.shelf.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 4, max = 15, message = "Name should be between 4 and 15 characters")
    private String name;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*^?&])[A-Za-z\\d@$!%*^?&]{8,}$\n",
            message = "Password must have uppercase letters, lowercase letters, digits, and special characters")
    private String password;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*^?&])[A-Za-z\\d@$!%*^?&]{8,}$\n",
            message = "Password must have uppercase letters, lowercase letters, digits, and special characters")
    private String confirmPassword;
}
