package com.danieltalik.fullStackApp.Models;

import com.fasterxml.jackson.annotation.JsonRootName;


import java.time.LocalDate;
import java.util.List;

@JsonRootName(value = "person")
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private int age;
    private String nickName;

    public Person(){

    }

    public Person(String firstName, String lastName, LocalDate birthday, String nickName){
        this.age = age;
        this.birthday = birthday;
        this.nickName = nickName;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getBirthday(){
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
@JsonRootName(value = "people")
class People{
    public List<Person> people;

    public List<Person> getPeople() {
        return people;
    }
}