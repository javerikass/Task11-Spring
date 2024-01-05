package ru.clevertec.bank.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.clevertec.bank.entity.User;

public interface UserDao {

    UUID createUser(User user);

    Optional<User> getUserById(UUID id);

    void updateUser(User user);

    void deleteUser(UUID id);

    List<User> findAll(int pageSize, int pageNumber);

}
