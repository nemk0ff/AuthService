package ru.t1.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
    @NotBlank(message = "username не должен быть пустым")
    String username,

    @NotBlank(message = "пароль не должен быть пустым")
    @Size(min = 8, max = 100)
    String password
) {
}