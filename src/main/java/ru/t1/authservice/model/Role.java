package ru.t1.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Role implements MyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", nullable = false, unique = true)
  private RoleName name;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  @Override
  public String toString() {
    return "Role{" +
        "name=" + name +
        '}';
  }

  public Role(Long id, RoleName name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }
}