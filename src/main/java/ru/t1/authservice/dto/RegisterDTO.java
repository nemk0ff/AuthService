package ru.t1.authservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import ru.senla.socialnetwork.model.users.Gender;
import ru.senla.socialnetwork.model.users.ProfileType;

public record RegisterDTO(
    @NotBlank(message = "email не должен быть пустым")
    String email,

    @NotBlank(message = "пароль не должен быть пустым")
    String password,

    @NotBlank(message = "введите ваше имя")
    String name,

    @NotBlank(message = "введите вашу фамилию")
    String surname,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate birthDate,

    Gender gender,

    String aboutMe,

    ProfileType profileType
) {
}