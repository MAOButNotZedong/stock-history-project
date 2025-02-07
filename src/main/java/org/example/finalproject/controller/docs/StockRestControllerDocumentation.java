package org.example.finalproject.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.dto.stock.HistoryResponseDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.exception.dto.ExceptionResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Stock controller", description = "Контроллер для работы с акциями")
@SecurityScheme(
        name = "jwt token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT"
)
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "401", description = "Пользователь неавторизован", content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionResponseDto.class))),
                @ApiResponse(responseCode = "500", description = "Внутренние проблемы с сервером", content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ExceptionResponseDto.class)))
        }

)

public interface StockRestControllerDocumentation {

    @Operation(
            summary = "Получение истории по акции",
            description = "Получение истории по конкретной акции для авторизованного пользователя",
            parameters = {
                    @Parameter(
                            name = EndPointPaths.TICKER,
                            example = "AAAA",
                            required = true
                    )
            }
    )

    ResponseEntity<HistoryResponseDto> getStockHistory(String ticker);


    @Operation(
            summary = "Сохранение истории по акции",
            description = "Сохранение истории торгов акцией за диапазон дат",
            requestBody = @RequestBody(
                    description = "Dto запроса",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StockSaveRequestDto.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Запрос не прошел валидацию полей Dto", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Тикер не найден", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDto.class)))

            }
    )
    ResponseEntity<Void> saveStock(StockSaveRequestDto stock);
}
