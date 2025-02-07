package org.example.finalproject.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto для возвращения имени и почты пользователя после регистрации")
public class RegisteredResponseDto {

    @Schema(example = "Name")
    private String username;
    @Schema(example = "abcd@mail.com")
    private String email;
}
