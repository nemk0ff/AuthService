package ru.t1.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
    @NotBlank(message = "email не должен быть пустым")
    String email,

    @NotBlank(message = "пароль не должен быть пустым")
    String password
) {
}