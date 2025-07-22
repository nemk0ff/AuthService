package ru.t1.authservice.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String email) {
    super(email + " уже используется");
  }
}
