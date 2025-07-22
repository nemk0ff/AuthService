package ru.t1.authservice.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.t1.authservice.dto.*;
import ru.t1.authservice.exceptions.*;
import ru.t1.authservice.model.*;
import ru.t1.authservice.repository.UserDao;
import ru.t1.authservice.services.AuthServiceImpl;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static ru.t1.authservice.unit.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @Mock
  private UserDao userDao;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthServiceImpl authService;

  private User testUser;
  private Role userRole;
  private AuthRequestDTO authRequest;
  private RegisterDTO registerDTO;

  @BeforeEach
  void setUp() {
    userRole = Role.builder()
        .id(1L)
        .name(RoleName.PREMIUM_USER)
        .description("Regular user")
        .build();

    testUser = User.builder()
        .id(1L)
        .username(TEST_USERNAME)
        .email(TEST_EMAIL_1)
        .password(TEST_PASSWORD)
        .roles(Set.of(userRole))
        .build();

    authRequest = new AuthRequestDTO(
        TEST_EMAIL_1,
        TEST_USERNAME,
        TEST_PASSWORD
    );

    registerDTO = new RegisterDTO(
        TEST_USERNAME,
        TEST_EMAIL_1,
        TEST_PASSWORD
    );
  }

  @Nested
  class GetAuthResponseTests {
    @Test
    void getAuthResponse_shouldReturnAuthResponse_whenCredentialsValid() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.of(testUser));
      when(passwordEncoder.matches(TEST_PASSWORD, testUser.getPassword())).thenReturn(true);

      AuthResponseDTO response = authService.getAuthResponse(authRequest);

      assertThat(response.token()).isNotBlank();
      assertThat(response.roles()).containsExactly("ROLE_[Role{name=PREMIUM_USER}]");
      verify(userDao).findByEmail(TEST_EMAIL_1);
      verify(passwordEncoder).matches(TEST_PASSWORD, testUser.getPassword());
    }

    @Test
    void getAuthResponse_shouldThrowException_whenPasswordInvalid() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.of(testUser));
      when(passwordEncoder.matches(TEST_PASSWORD, testUser.getPassword())).thenReturn(false);

      assertThatThrownBy(() -> authService.getAuthResponse(authRequest))
          .isInstanceOf(IllegalPasswordException.class);
    }

    @Test
    void getAuthResponse_shouldThrowException_whenUserNotFound() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> authService.getAuthResponse(authRequest))
          .isInstanceOf(UserNotRegisteredException.class)
          .hasMessageContaining(TEST_EMAIL_1);
    }
  }

  @Nested
  class RegisterTests {
    @Test
    void register_shouldReturnUserResponse_whenRegistrationSuccessful() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.empty());
      when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn("encoded_password");
      when(userDao.saveOrUpdate(any(User.class))).thenReturn(testUser);

      UserResponseDTO response = authService.register(registerDTO);

      assertThat(response.email()).isEqualTo(TEST_EMAIL_1);
      assertThat(response.username()).isEqualTo(TEST_USERNAME);
      verify(userDao).findByEmail(TEST_EMAIL_1);
      verify(passwordEncoder).encode(TEST_PASSWORD);
      verify(userDao).saveOrUpdate(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenEmailExists() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.of(testUser));

      assertThatThrownBy(() -> authService.register(registerDTO))
          .isInstanceOf(EmailAlreadyExistsException.class)
          .hasMessageContaining(TEST_EMAIL_1);
    }
  }

  @Nested
  class LoadUserByUsernameTests {
    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.of(testUser));

      UserDetails userDetails = authService.loadUserByUsername(TEST_EMAIL_1);

      assertThat(userDetails.getUsername()).isEqualTo(TEST_EMAIL_1);
      assertThat(userDetails.getPassword()).isEqualTo(TEST_PASSWORD);
      assertThat(userDetails.getAuthorities())
          .extracting("authority")
          .containsExactly("ROLE_[Role{name=PREMIUM_USER}]");
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
      when(userDao.findByEmail(TEST_EMAIL_1)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> authService.loadUserByUsername(TEST_EMAIL_1))
          .isInstanceOf(UserNotRegisteredException.class)
          .hasMessageContaining(TEST_EMAIL_1);
    }
  }
}