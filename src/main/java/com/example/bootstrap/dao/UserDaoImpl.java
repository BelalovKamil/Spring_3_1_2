package com.example.bootstrap.dao;

import com.example.bootstrap.model.Role;
import com.example.bootstrap.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateUserById(long id, User updateUser) {
        entityManager.createQuery("update User set email = :email" +
                        ", name = :name, surname = :surname, age = :age, password = :password where id = :id")
                .setParameter("id", id)
                .setParameter("name", updateUser.getName())
                .setParameter("surname", updateUser.getSurname())
                .setParameter("email", updateUser.getEmail())
                .setParameter("age", updateUser.getAge())
                .setParameter("password", BCrypt.hashpw(updateUser.getPassword(), BCrypt.gensalt()))
                .executeUpdate();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        List<User> userList = entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = entityManager.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public void factoryReset() {
        entityManager.createQuery("delete from User").executeUpdate();
        entityManager.createQuery("delete from Role").executeUpdate();
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(adminRole);
        roleSet.add(userRole);
        User admin = new User("admin", BCrypt.hashpw("admin", BCrypt.gensalt()));
        admin.setSurname("Sergeev");
        admin.setEmail("mail@mail.com");
        admin.setAge((short) 25);
        admin.setRoleSet(roleSet);
        entityManager.persist(admin);
    }
}
