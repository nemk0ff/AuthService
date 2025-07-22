package ru.t1.authservice.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.t1.authservice.model.Role;
import ru.t1.authservice.model.User;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
  UserResponseDTO toUserResponseDTO(User user);

  User toUser(RegisterDTO registrationDTO);

  default List<String> mapRoles(Set<Role> roles) {
    if (roles == null) {
      return Collections.emptyList();
    }
    return roles.stream()
        .map(role -> "ROLE_" + role.getName().name())
        .collect(Collectors.toList());
  }
}