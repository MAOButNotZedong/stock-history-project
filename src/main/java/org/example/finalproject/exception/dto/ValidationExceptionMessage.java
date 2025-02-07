package org.example.finalproject.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Schema(description = "Информация об ошибке из-за валидации")
public class ValidationExceptionMessage {
    private String fieldName;
    private String message;
}
