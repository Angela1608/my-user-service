package user.service.service.mapper;

import org.springframework.stereotype.Component;
import user.service.dto.request.UserRequestDto;
import user.service.dto.response.UserResponseDto;
import user.service.model.User;

@Component
public class UserDtoMapper {
    public User toModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setBirthDate(userRequestDto.getBirthDate());
        user.setAddress(userRequestDto.getAddress());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setBirthDate(user.getBirthDate());
        userResponseDto.setAddress(user.getAddress());
        return userResponseDto;
    }
}
