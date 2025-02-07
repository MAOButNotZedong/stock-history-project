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
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.constants.ExceptionConstants;
import org.example.finalproject.dto.JwtTokenResponseDto;
import org.example.finalproject.dto.stock.StockSaveRequestDto;
import org.example.finalproject.dto.user.RegisteredResponseDto;
import org.example.finalproject.dto.user.UserLoginDto;
import org.example.finalproject.dto.user.UserRegistrationDto;
import org.example.finalproject.exception.dto.ExceptionResponseDto;
import org.example.finalproject.exception.dto.MultiExceptionResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "User controller", description = "Контроллер для работы c пользователем (регистрация, авторизация)")
@ApiResponses(
        @ApiResponse(responseCode = "500", description = "Внутренние проблемы с сервером", content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ExceptionResponseDto.class)))
)
public interface UserRestControllerDocumentation {

    @Operation(
            summary = "Авторизация пользователя",
            description = "Авторизация пользователя для получения jwt токена",
            requestBody = @RequestBody(
                    description = "Dto запроса",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserLoginDto.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtTokenResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Неправильные логин и/или пароль", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    ResponseEntity<JwtTokenResponseDto> login(UserLoginDto user);

    @Operation(
            summary = "Регистрация пользователя",
            requestBody = @RequestBody(
                    description = "Dto запроса",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserRegistrationDto.class)
                    ),
                    required = true
            ),
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisteredResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Запрос не прошел валидацию полей Dto", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MultiExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже зарегистрирован", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    ResponseEntity<RegisteredResponseDto> register(UserRegistrationDto user);
}
