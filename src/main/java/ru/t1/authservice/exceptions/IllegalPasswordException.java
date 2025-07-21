package ru.t1.authservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class IllegalPasswordException extends AuthenticationException {
  public IllegalPasswordException() {
    super("Вы ввели неверный пароль");
  }
}
