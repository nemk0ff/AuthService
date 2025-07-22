package ru.t1.authservice.dto;

import java.util.List;

public record AuthResponseDTO(
    List<String> roles,
    String token
) {
}