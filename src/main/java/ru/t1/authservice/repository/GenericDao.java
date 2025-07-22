package ru.t1.authservice.repository;

import java.util.Optional;
import ru.t1.authservice.model.MyEntity;

public interface GenericDao<T extends MyEntity> {
  T saveOrUpdate(T entity);

  Optional<T> find(Long id);

  void delete(T entity);
}