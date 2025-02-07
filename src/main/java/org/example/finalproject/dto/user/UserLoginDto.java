package org.example.finalproject.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalproject.constants.ValidationConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto для авторизации пользователя")
public class UserLoginDto {

    @Schema(example = "abcd@mail.com")
    private String email;

    @Schema(example = "1234")
    private String password;
}
