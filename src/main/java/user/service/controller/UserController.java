package user.service.controller;

import user.service.model.User;
import user.service.service.impl.UserServiceImpl;
import user.service.service.mapper.UserDtoMapper;
import user.service.dto.request.UserRequestDto;
import user.service.dto.response.UserResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;
    private final UserDtoMapper mapper;

    public UserController(UserServiceImpl userService, UserDtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public UserResponseDto save(
            @Valid @RequestBody UserRequestDto requestDto) {
        User user = userService.save(mapper.toModel(requestDto));
        return mapper.toDto(user);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @Valid @RequestBody UserRequestDto requestDto) {
        User user = mapper.toModel(requestDto);
        user.setId(id);
        return mapper.toDto(userService.save(user));
    }

    @PutMapping("/{id}/address")
    public UserResponseDto updateAddress(@RequestParam String address,
                                         @PathVariable Long id) {
        User user = userService.getById(id);
        user.setAddress(address);
        return mapper.toDto(userService.save(user));
    }

    @GetMapping
    public List<UserResponseDto> findAllByBirthDateBetween(@RequestParam  @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                                       LocalDate from,
                                                           @RequestParam  @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                                   LocalDate to) {
        return userService
                .findAllByBirthDateBetween(from, to)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {

        userService.deleteById(id);
        return "The user is deleted";
    }
}
