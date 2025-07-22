package ru.t1.authservice.repository;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import ru.t1.authservice.model.User;

@Repository
@Slf4j
public class UserDaoImpl extends HibernateAbstractDao<User> implements UserDao {

  public UserDaoImpl(SessionFactory sessionFactory) {
    super(User.class, sessionFactory);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    log.info("Поиск пользователя по username {}", email);
    try {
      Optional<User> user = Optional.ofNullable(
          sessionFactory.getCurrentSession()
              .createNamedQuery("User.findByEmail", User.class)
              .setParameter("username", email)
              .uniqueResult());
      if (user.isPresent()) {
        log.info("Найден пользователь с username {}: id={}", email, user.get().getId());
      } else {
        log.info("Пользователь с username {} не найден", email);
      }
      return user;
    } catch (HibernateException e) {
      throw new DataRetrievalFailureException("Ошибка при поиске пользователя " + email, e);
    }
  }
}