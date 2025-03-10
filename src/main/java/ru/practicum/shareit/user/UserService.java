package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsersList() {
        return userRepository.getAllUsersList().stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto getUserById(long id) {
        return UserMapper.toUserDto(userRepository.getUserById(id));
    }

    public UserDto addUser(UserCreateDto userCreateDto) {
        User user = userRepository.addUser(UserMapper.toUser(userCreateDto));
        return UserMapper.toUserDto(user);
    }

    public UserDto updateUser(Long userId, UserUpdateDto userUpdateDto) {
        User user = UserMapper.toUser(userUpdateDto);
        user.setId(userId);
       return UserMapper.toUserDto(userRepository.updateUser(user));
    }

    public void deleteUserById(long id) {
        userRepository.deleteUserById(id);
    }
}
