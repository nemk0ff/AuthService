package ru.t1.authservice.repository;

import java.util.Optional;
import ru.t1.authservice.model.User;

public interface UserDao extends GenericDao<User> {
  Optional<User> findByEmail(String email);
}
