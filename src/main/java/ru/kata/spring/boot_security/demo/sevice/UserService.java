package ru.kata.spring.boot_security.demo.sevice;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);
    List<User> findAll();
    User findById(Long id);
    void delete(Long id);
    User findByUsername(String username);
}