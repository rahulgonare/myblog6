package com.event.manager.EventManger.repository;

import com.event.manager.EventManger.entity.Temporary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryRepository extends JpaRepository<Temporary, Long> {
    Temporary findByEmail(String email);

    boolean existsByEmail(String email);
}
