package ru.t1.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для аутентификации")
public record AuthRequestDTO(
    @Schema(description = "Email пользователя", example = "example@senla.ru")
    @Email(message = "Вы ввели неверный формат email")
    @NotBlank(message = "Почта не может быть пустой")
    String email,

    @Schema(description = "Username пользователя", example = "ivanov_user")
    @NotBlank(message = "Username не может быть пустым")
    String username,

    @Schema(description = "Пароль", example = "password123", minLength = 8, maxLength = 100)
    @NotBlank(message = "Пароль не может быть пустым")
    String password
) {
}