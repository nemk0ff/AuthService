package ru.t1.authservice.dto;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.t1.authservice.model.User;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserResponseDTO toUserResponseDTO(User user);

  List<UserResponseDTO> toListUserResponseDTO(List<User> users);

  User toUser(RegisterDTO registrationDTO);
}