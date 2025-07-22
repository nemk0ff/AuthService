package ru.t1.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import ru.t1.authservice.model.Role;

@Schema(description = "Ответ с данными аутентификации")
public record AuthResponseDTO(
    @Schema(description = "Роль пользователя", example = "USER")
    List<String> roles,

    @Schema(description = "JWT токен", example = "jwt.token.for.senla.user")
    String token
) {
}