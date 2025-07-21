package ru.t1.authservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.t1.authservice.dto.AuthRequestDTO;
import ru.t1.authservice.dto.AuthResponseDTO;
import ru.t1.authservice.dto.RegisterDTO;

public interface AuthService extends UserDetailsService {
  AuthResponseDTO getAuthResponse(AuthRequestDTO requestDTO);

  UserResponseDTO register(RegisterDTO registerDTO);
}
