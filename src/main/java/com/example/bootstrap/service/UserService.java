package com.example.bootstrap.service;

import com.example.bootstrap.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void removeUserById(long id);

    List<User> getAllUsers();

    void updateUserById(long id, User user);

    User getUserById(long id);

    User getUserByName (String name);

    void factoryReset();
}
