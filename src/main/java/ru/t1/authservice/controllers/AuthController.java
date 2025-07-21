package ru.t1.authservice.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthController {
  ResponseEntity<?> register(@Valid RegisterDTO registerDTO);

  ResponseEntity<?> login(@Valid AuthRequestDTO request);
}