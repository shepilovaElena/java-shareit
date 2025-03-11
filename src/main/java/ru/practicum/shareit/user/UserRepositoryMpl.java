package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryMpl implements UserRepository {
    Map<Long,User> userMap = new HashMap<>();
    long count = 0;

    @Override
    public List<User> getAllUsersList() {
        return userMap.values().stream().toList();
    }

    @Override
    public User getUserById(long id) {
        checkUserId(id);
        return userMap.get(id);
    }

    @Override
    public User addUser(User user) {
        validateUserEmail(user);

        count++;
        user.setId(count);
        userMap.put(count, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        validateUserEmail(user);
        long id = user.getId();
        checkUserId(id);

            if (user.getName() != null && user.getEmail() != null) {
                userMap.put(id, user);
                return user;
            }
            if (user.getName() == null) {
                user.setName(userMap.get(id).getName());
            }
            if (user.getEmail() == null) {
                user.setEmail(userMap.get(id).getEmail());
            }

            userMap.put(id, user);
            return user;
    }

    @Override
    public void deleteUserById(long id) {
        checkUserId(id);
        userMap.remove(id);
    }

    private void validateUserEmail(User user) {
        Optional<User> userOptional = userMap.values().stream()
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .findFirst();

        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("User with the email "  + user.getEmail() + " already exist");
        }
    }

    private void checkUserId(long userId) {
        if (!userMap.containsKey(userId)) {
            throw new NoSuchElementException("User with id = " + userId + " not found.");
        }
    }
}
