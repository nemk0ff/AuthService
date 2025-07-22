package ru.t1.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDTO(
    @Email(message = "Вы ввели неверный формат username")
    @NotBlank(message = "Почта не может быть пустой")
    String username,

    @NotBlank(message = "Пароль не может быть пустым")
    String password
) {
}