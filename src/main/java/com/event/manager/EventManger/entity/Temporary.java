package com.event.manager.EventManger.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEMP")
public class Temporary {

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

    @JsonProperty
    @Column(name = "verified")
    private boolean isVerified;

}
