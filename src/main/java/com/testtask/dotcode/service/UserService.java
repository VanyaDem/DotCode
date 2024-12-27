package com.testtask.dotcode.service;

import com.testtask.dotcode.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    Page<User> findAll(PageRequest request);

    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);

}
