package ru.t1.authservice.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.authservice.dto.AuthRequestDTO;
import ru.t1.authservice.dto.AuthResponseDTO;
import ru.t1.authservice.dto.RegisterDTO;
import ru.t1.authservice.dto.UserResponseDTO;
import ru.t1.authservice.services.AuthService;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  @Override
  public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDTO request) {
    log.info("Попытка входа пользователя с username: {}...", request.username());
    AuthResponseDTO response = authService.getAuthResponse(request);
    log.info("Успешный вход пользователя с username: {}.", request.username());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/register")
  @Override
  public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
    log.info("Попытка регистрации нового пользователя с username: {}...", registerDTO.username());
    UserResponseDTO response = authService.register(registerDTO);
    log.info("Успешная регистрация пользователя с username: {}", registerDTO.username());
    return ResponseEntity.ok(response);
  }
}