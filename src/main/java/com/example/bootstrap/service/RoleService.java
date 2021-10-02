package com.example.bootstrap.service;

import com.example.bootstrap.model.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    List<Role> getAllRole();
}
