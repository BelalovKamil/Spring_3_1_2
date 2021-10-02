package com.example.bootstrap.dao;

import com.example.bootstrap.model.Role;

import java.util.List;

public interface RoleDao {

    Role getRoleByName (String name);
    List<Role> getAllRole();
}
