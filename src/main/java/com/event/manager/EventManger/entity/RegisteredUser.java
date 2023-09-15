package com.event.manager.EventManger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registered_user")
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "grade")
    private int grade;

    @Column(name = "phone")
    private String phone;

    @Column(name = "state")
    private String state;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "city")
    private String city;

}
