package ru.t1.authservice.model;

public enum RoleName {
  ADMIN("admin"),
  PREMIUM_USER("premium_user"),
  GUEST("guest");

  private final String name;

  RoleName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}