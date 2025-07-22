package ru.t1.authservice.dto;

public record AuthResponseDTO(
    String[] role,
    String token
) {
}