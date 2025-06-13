package com.sidyenni.chatapp.repositories;

import com.sidyenni.chatapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(String roleValue);
}
