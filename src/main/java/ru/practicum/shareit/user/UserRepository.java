package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsersList();

    User getUserById(long id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUserById(long id);
}
