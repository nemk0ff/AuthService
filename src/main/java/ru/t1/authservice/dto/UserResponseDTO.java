package ru.t1.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.t1.authservice.model.Role;

@Schema(description = "Данные зарегистрированного пользователя")
public record UserResponseDTO(
    @Schema(description = "ID пользователя", example = "123")
    Long id,

    @Schema(description = "Email", example = "example@senla.ru")
    String username,

    @Schema(description = "Роль", example = "USER")
    Role role) {
}