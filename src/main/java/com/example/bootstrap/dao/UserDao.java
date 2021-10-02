package com.example.bootstrap.dao;

import com.example.bootstrap.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void removeUserById(long id);

    void updateUserById(long id, User user);

    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByName (String name);

    User getUserByEmail (String email);

    void factoryReset();
}
