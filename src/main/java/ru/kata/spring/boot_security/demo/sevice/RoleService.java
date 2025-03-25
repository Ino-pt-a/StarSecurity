package ru.kata.spring.boot_security.demo.sevice;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);
    List<Role> findAll();
    Role findById(Long id);

}
