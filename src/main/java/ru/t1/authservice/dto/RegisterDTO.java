package ru.t1.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для регистрации нового пользователя")
public record RegisterDTO(
    @Schema(description = "Username", example = "ivanov_user")
    @NotBlank(message = "username не должен быть пустым")
    String username,

    @Schema(description = "Email", example = "example@senla.ru")
    @NotBlank(message = "email не должен быть пустым")
    String email,

    @Schema(description = "Пароль", example = "password123")
    @NotBlank(message = "пароль не должен быть пустым")
    @Size(min = 8, max = 100)
    String password
) {
}