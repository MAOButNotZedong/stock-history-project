package org.example.finalproject.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Dto с информацией об ошибке")
public class ExceptionResponseDto {
    @Schema(example = "Unauthorized")
    private String status;
    @Schema(example = "...BadCredentialsException.class")
    private String exceptionType;
    @Schema(example = "Email or password not valid")
    private String message;


}
