package org.example.finalproject.exception.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "Dto с информацией о нескольких ошибках")
public class MultiExceptionResponseDto {
    private String status;
    private List<Object> messages = new ArrayList<>();
}
