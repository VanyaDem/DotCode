package com.testtask.dotcode.service;

import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.domain.repository.UserRepository;
import com.testtask.dotcode.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Page<User> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public User findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(getException(id));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    public User update(Long id, User user) {
        var findedUser = findById(id);
        findedUser.setFirstName(user.getFirstName());
        findedUser.setLastName(user.getLastName());
        findedUser.setEmail(user.getEmail());
        return repository.save(findedUser);
    }

    @Override
    public void delete(Long id) {
        var user = findById(id);
        repository.delete(user);
    }

    private Supplier<UserNotFoundException> getException(Long id) {
        return () -> new UserNotFoundException(String.format("User with id: %s does not exist.", id));
    }
}
