package com.event.manager.EventManger.repository;

import com.event.manager.EventManger.entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    RegisteredUser findByEmail(String email);

    boolean existsByEmail(String email);
}
