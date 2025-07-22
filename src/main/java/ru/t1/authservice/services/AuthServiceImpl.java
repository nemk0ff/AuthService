package ru.t1.authservice.services;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.authservice.dto.AuthRequestDTO;
import ru.t1.authservice.dto.AuthResponseDTO;
import ru.t1.authservice.dto.RegisterDTO;
import ru.t1.authservice.dto.UserMapper;
import ru.t1.authservice.dto.UserResponseDTO;
import ru.t1.authservice.exceptions.EmailAlreadyExistsException;
import ru.t1.authservice.exceptions.IllegalPasswordException;
import ru.t1.authservice.exceptions.UserNotRegisteredException;
import ru.t1.authservice.model.User;
import ru.t1.authservice.repository.UserDao;
import ru.t1.authservice.security.JwtUtils;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public AuthResponseDTO getAuthResponse(AuthRequestDTO requestDTO) {
    log.info("Проверяем логин и пароль пользователя {}...", requestDTO.username());
    UserDetails correctDetails = loadUserByUsername(requestDTO.username());

    if (!passwordEncoder.matches(requestDTO.password(), correctDetails.getPassword())) {
      throw new IllegalPasswordException();
    }

    String role = correctDetails.getAuthorities()
        .iterator().next()
        .getAuthority();
    String token = JwtUtils.generateToken(requestDTO.username(), role);
    log.info("Токен сгенерирован успешно.");
    return new AuthResponseDTO(List.of(role), token);
  }

  @Transactional
  @Override
  public UserResponseDTO register(RegisterDTO regDTO) {
    log.info("Регистрируем нового пользователя {}...", regDTO.username());
    if (userDao.findByEmail(regDTO.username()).isPresent()) {
      throw new EmailAlreadyExistsException(regDTO.username());
    }
    User user = UserMapper.INSTANCE.toUser(regDTO);
    user.setPassword(passwordEncoder.encode(regDTO.password()));

    user = userDao.saveOrUpdate(user);
    log.info("Пользователь {} успешно зарегистрирован.", regDTO.username());
    return UserMapper.INSTANCE.toUserResponseDTO(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.debug("Поиск пользователя по username: {}", email);
    User user = userDao.findByEmail(email).orElseThrow(
        () -> new UserNotRegisteredException(email));

    log.debug("Пользователь найден: {}", user);
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(String.valueOf(user.getRoles()))
        .build();
  }
}
