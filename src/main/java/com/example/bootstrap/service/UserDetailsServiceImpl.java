package com.example.bootstrap.service;

import com.example.bootstrap.dao.UserDao;
import com.example.bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = userDao.getUserByEmail(email);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + email);
        }
        return myUser;
    }
}