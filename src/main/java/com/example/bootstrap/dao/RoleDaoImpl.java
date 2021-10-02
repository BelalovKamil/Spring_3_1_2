package com.example.bootstrap.dao;

import com.example.bootstrap.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String name) {
        name = "ROLE_" + name.toUpperCase();
        return entityManager.createQuery("select u from Role u where u.role = :role", Role.class)
                .setParameter("role", name)
                .getSingleResult();
    }

    @Override
    public List<Role> getAllRole() {
        return entityManager.createQuery("select u from Role u", Role.class).getResultList();
    }
}
