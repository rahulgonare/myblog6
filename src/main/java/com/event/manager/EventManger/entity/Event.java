package com.event.manager.EventManger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_registration")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private RegisteredUser registeredUser;

    @Column(name = "create_date")
    private LocalDateTime localTime;

}
