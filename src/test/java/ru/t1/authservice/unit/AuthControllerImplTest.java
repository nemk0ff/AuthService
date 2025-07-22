package ru.t1.authservice.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.t1.authservice.controllers.AuthControllerImpl;
import ru.t1.authservice.dto.*;
import ru.t1.authservice.exceptions.*;
import ru.t1.authservice.services.AuthService;

import java.util.List;

import static ru.t1.authservice.unit.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerImplTest {

  @Mock
  private AuthService authService;

  @InjectMocks
  private AuthControllerImpl authController;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler())
        .build();
  }

  @Nested
  class LoginTests {
    @Test
    void login_shouldReturnAuthResponse_whenCredentialsValid() throws Exception {
      AuthRequestDTO request = new AuthRequestDTO(
          TEST_EMAIL_1,
          TEST_USERNAME,
          TEST_PASSWORD
      );

      AuthResponseDTO response = new AuthResponseDTO(
          List.of("ROLE_USER"),
          "test.jwt.token"
      );

      when(authService.getAuthResponse(request)).thenReturn(response);

      mockMvc.perform(post("/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.token").value("test.jwt.token"))
          .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void login_shouldReturnUnauthorized_whenInvalidCredentials() throws Exception {
      AuthRequestDTO request = new AuthRequestDTO(
          TEST_EMAIL_1,
          TEST_USERNAME,
          "wrong_password"
      );

      when(authService.getAuthResponse(request))
          .thenThrow(new IllegalPasswordException());

      mockMvc.perform(post("/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isUnauthorized());
    }

    @Test
    void login_shouldReturnBadRequest_whenValidationFails() throws Exception {
      AuthRequestDTO invalidRequest = new AuthRequestDTO(
          "invalid-email",
          "",
          ""
      );

      mockMvc.perform(post("/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(invalidRequest)))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class RegisterTests {
    @Test
    void register_shouldReturnUserResponse_whenRegistrationSuccessful() throws Exception {
      RegisterDTO request = new RegisterDTO(
          TEST_USERNAME,
          TEST_EMAIL_1,
          TEST_PASSWORD
      );

      UserResponseDTO response = new UserResponseDTO(
          1L,
          TEST_EMAIL_1,
          TEST_USERNAME,
          List.of("USER")
      );

      when(authService.register(request)).thenReturn(response);

      mockMvc.perform(post("/auth/register")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1))
          .andExpect(jsonPath("$.email").value(TEST_EMAIL_1))
          .andExpect(jsonPath("$.username").value(TEST_USERNAME));
    }

    @Test
    void register_shouldReturnConflict_whenEmailAlreadyExists() throws Exception {
      RegisterDTO request = new RegisterDTO(
          TEST_USERNAME,
          TEST_EMAIL_1,
          TEST_PASSWORD
      );

      when(authService.register(request))
          .thenThrow(new EmailAlreadyExistsException(TEST_EMAIL_1));

      mockMvc.perform(post("/auth/register")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isUnauthorized());
    }

    @Test
    void register_shouldReturnBadRequest_whenValidationFails() throws Exception {
      RegisterDTO invalidRequest = new RegisterDTO(
          "",
          "invalid-email",
          "short"
      );

      mockMvc.perform(post("/auth/register")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(invalidRequest)))
          .andExpect(status().isBadRequest());
    }
  }
}