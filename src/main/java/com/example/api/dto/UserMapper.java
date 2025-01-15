package com.example.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.api.entities.User;

public class UserMapper {

    public static User transformDtoIntoEntity(UserRequestDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        return user;
    }

    public static UserResponseDto transformEntityToResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public static List<UserResponseDto> transformEntityToListResponseDto(List<User> users) {
        List<UserResponseDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserResponseDto userDto = new UserResponseDto(user.getId(), user.getName(), user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public static List<User> transformDtoToEntity(List<UserResponseDto> usersDto) {
        List<User> userList = new ArrayList<>();
        for (UserResponseDto user : usersDto) {
            User userEntity = new User();
            userEntity.setId(user.id());
            userEntity.setName(user.name());
            userEntity.setEmail(user.email());
            userList.add(userEntity);
        }
        return userList;
    }
}
