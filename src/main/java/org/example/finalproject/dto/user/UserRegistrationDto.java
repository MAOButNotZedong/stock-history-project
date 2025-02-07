package org.example.finalproject.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalproject.constants.ValidationConstants;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Schema(description = "Dto для регистрации пользователя")
public class UserRegistrationDto {

    @NotBlank
    @Size(min = ValidationConstants.MIN,
            max = ValidationConstants.USERNAME_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.USERNAME_PATTERN)
    @Schema(example = "Name")
    private String username;

    @NotBlank
    @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH,
            max = ValidationConstants.PASSWORD_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.PASSWORD_PATTERN)
    @Schema(example = "1234")
    private String password;

    @NotBlank
    @Size(min = ValidationConstants.MIN,
            max = ValidationConstants.EMAIL_MAX_LENGTH)
    @Pattern(regexp = ValidationConstants.EMAIL_PATTERN)
    @Schema(example = "abcd@mail.com")
    private String email;

}
