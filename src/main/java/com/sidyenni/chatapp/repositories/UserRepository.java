package com.sidyenni.chatapp.repositories;

import com.sidyenni.chatapp.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Override
    List<User> findAll();
}
