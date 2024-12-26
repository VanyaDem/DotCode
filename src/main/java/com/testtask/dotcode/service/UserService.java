package com.testtask.dotcode.service;

import com.testtask.dotcode.domain.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void delete(User user);


}
