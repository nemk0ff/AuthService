package ru.t1.authservice.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ru.senla.socialnetwork.dao.GenericDao;
import ru.senla.socialnetwork.model.users.Gender;
import ru.senla.socialnetwork.model.users.User;

public interface UserDao extends GenericDao<User> {
  List<User> findByParam(String name, String surname, Gender gender, LocalDate birthdate);

  Optional<User> findByEmail(String email);
}
