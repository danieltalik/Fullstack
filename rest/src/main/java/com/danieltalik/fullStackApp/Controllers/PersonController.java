package com.danieltalik.fullStackApp.Controllers;


import com.danieltalik.fullStackApp.DAL.BirthdayDAL;
import com.danieltalik.fullStackApp.DAL.IDao;
import com.danieltalik.fullStackApp.DAL.NicknameDAL;
import com.danieltalik.fullStackApp.Models.Person;
import com.danieltalik.fullStackApp.config.DataAccessConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PersonController {

    private IDao birthdayDAL;
    private IDao nicknameDAL;

    public PersonController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataAccessConfig.class);
        this.birthdayDAL = context.getBean("birthdayDAL", BirthdayDAL.class);
        this.nicknameDAL = context.getBean("nicknameDAL", NicknameDAL.class);
    }

    @GetMapping("/byNickname")
    public Person getPerson(@RequestParam String nickname) {
        return nicknameDAL.getName(nickname);
    }

    @GetMapping(path = "/everyone", produces = "application/json")
    public List<Person> getBirthdays() {
        return birthdayDAL.getAll();
    }

    @GetMapping("/byBirthday")
    public Person getBirthdayPerson(@RequestParam String birthday) {
        return birthdayDAL.getName(birthday);
    }

    @GetMapping("/byName")
    public Person getBirthday(@RequestParam String first, @RequestParam String last) {
        return birthdayDAL.getDetails(first, last);
    }

    @PostMapping(path = "/addPerson", consumes = "application/json")
    public boolean addPerson(@RequestBody Person person) {
        person.setAge(setAge(person.getBirthday()));
        return addBirthday(person) && addNickname(person);
    }

    public int setAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    private boolean addBirthday(Person person) {
        return birthdayDAL.insertPerson(person.getFirstName()
                , person.getLastName()
                , person.getNickName()
                , person.getAge()
                , person.getBirthday());
    }

    private boolean addNickname(Person person) {
        return nicknameDAL.insertPerson(
                person.getFirstName()
                , person.getLastName()
                , person.getNickName()
                , person.getAge()
                , person.getBirthday());
    }

}
