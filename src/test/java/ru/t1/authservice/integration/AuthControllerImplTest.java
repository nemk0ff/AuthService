package ru.t1.authservice.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.t1.authservice.dto.AuthRequestDTO;
import ru.t1.authservice.dto.RegisterDTO;

public class AuthControllerImplTest extends BaseIntegrationTest {

  @Test
  @DisplayName("POST /auth/login - успешная аутентификация")
  void loginWithEmail_Success() throws Exception {
    AuthRequestDTO request = new AuthRequestDTO(
        "admin@example.com",
        "admin_user",
        "user_bookstore"
    );

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.roles[0]").value("ROLE_[Role{name=ADMIN}]")); // Оригинальное название роли
  }

  @Test
  @DisplayName("POST /auth/login - неверные учетные данные")
  void login_InvalidCredentials() throws Exception {
    AuthRequestDTO request = new AuthRequestDTO(
        "admin@example.com",
        "admin_user",
        "wrong_password"
    );

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @DisplayName("POST /auth/register - успешная регистрация")
  void register_Success() throws Exception {
    RegisterDTO registerDTO = new RegisterDTO(
        "new_user",
        "newuser@example.com",
        "password123"
    );

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("newuser@example.com"))
        .andExpect(jsonPath("$.username").value("new_user"))
        .andExpect(jsonPath("$.roles").isArray());
  }

  @Test
  @DisplayName("POST /auth/register - невалидные данные (пустой email)")
  void register_InvalidData() throws Exception {
    RegisterDTO invalidRegisterDTO = new RegisterDTO(
        "invalid_user",
        "",
        "password123"
    );

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRegisterDTO)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("POST /auth/register - email уже существует")
  void register_EmailAlreadyExists() throws Exception {
    RegisterDTO registerDTO = new RegisterDTO(
        "admin_user",
        "admin@example.com",  // email уже существует в БД
        "user_password"
    );

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerDTO)))
        .andExpect(status().isUnauthorized());
  }
}