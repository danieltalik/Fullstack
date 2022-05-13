package com.danieltalik.fullStackApp.Controllers;


import com.danieltalik.fullStackApp.DAL.BirthdayDAL;
import com.danieltalik.fullStackApp.DAL.IDao;
import com.danieltalik.fullStackApp.DAL.NicknameDAL;
import com.danieltalik.fullStackApp.Models.Person;
import com.danieltalik.fullStackApp.Models.SOAP.PersonXML;
import com.danieltalik.fullStackApp.config.DataAccessConfig;
import com.danieltalik.fullStackApp.handlers.SOAPConversionHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BirthdayController {

    private IDao birthdayDAL;
    private IDao nicknameDAL;
    public BirthdayController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataAccessConfig.class);
        this.birthdayDAL = context.getBean("birthdayDAL", BirthdayDAL.class);
        this.nicknameDAL = context.getBean("nicknameDAL", NicknameDAL.class);
    }
    @GetMapping("/allBirthdays")
    public List<Person> getBirthdays(){
        return birthdayDAL.getAll();
    }

    @GetMapping("/birthdayPerson")
    public Person getBirthdayPerson(@RequestParam String birthday){

        //TODO: Transform the string so it is uniform for SQL query
        Person result = birthdayDAL.getName(birthday);
        return result;
    }
    @GetMapping("/personBirthday")
    public Person getBirthday(@RequestParam String first, @RequestParam String last){
        Person result = birthdayDAL.getDetails(first,last);
        return result;
    }
    @PostMapping(path = "/addBirthday", consumes = "application/json")
    public Person addPerson(@RequestBody Person person) {
        System.out.println(person);
        person.setAge(setAge(person.getBirthday()));

        birthdayDAL.insertPerson(person.getFirstName()
                ,person.getLastName()
                ,person.getNickName()
                ,person.getAge()
                ,person.getBirthday());

        nicknameDAL.insertPerson(
                person.getFirstName()
                ,person.getLastName()
                ,person.getNickName()
                ,person.getAge()
                ,person.getBirthday());

        return birthdayDAL.getDetails(person.getFirstName(),person.getLastName());
    }
    @PostMapping(path = "/addSoap", consumes = "application/xml",produces = "application/json")
    public PersonXML addSoap(@RequestBody String request){

        PersonXML person = new SOAPConversionHandler().convertToPOJO(request);
        /*birthdayDAL.insertPerson(person.getFirstName()
                ,person.getLastName()
                ,person.getNickName()
                ,person.getAge()
                ,person.getBirthday());

        nicknameDAL.insertPerson(
                person.getFirstName()
                ,person.getLastName()
                ,person.getNickName()
                ,person.getAge()
                ,person.getBirthday());

        return birthdayDAL.getDetails(person.getFirstName(),person.getLastName());

         */
        return person;
    }

    public int setAge(LocalDate birthday){
        Period period = Period.between(birthday,LocalDate.now());
        return period.getYears();
    }

}