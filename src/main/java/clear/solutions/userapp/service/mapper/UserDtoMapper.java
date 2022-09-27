package clear.solutions.userapp.service.mapper;

import clear.solutions.userapp.dto.request.UserRequestDto;
import clear.solutions.userapp.dto.response.UserResponseDto;
import clear.solutions.userapp.model.User;
import org.springframework.stereotype.Component;

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
