package com.danieltalik.fullStackApp.DAL;

import com.danieltalik.fullStackApp.Models.Person;

import java.time.LocalDate;
import java.util.List;

public interface IDao {
    Person getName(String p);
    Person getDetails(String first, String last);
    List<Person> getAll();
    boolean insertPerson(String first, String last, String nickname, int age, LocalDate birthday);
}
