package com.danieltalik.fullstackapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    protected Person() {
        // for JPA
    }

    public Person(String firstName, String lastName, String nickname, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
