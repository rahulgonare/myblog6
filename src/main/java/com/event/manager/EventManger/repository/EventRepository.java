package com.event.manager.EventManger.repository;

import com.event.manager.EventManger.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
