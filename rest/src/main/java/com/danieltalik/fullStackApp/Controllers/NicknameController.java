package com.danieltalik.fullStackApp.Controllers;

import com.danieltalik.fullStackApp.config.DataAccessConfig;
import com.danieltalik.fullStackApp.DAL.IDao;
import com.danieltalik.fullStackApp.DAL.NicknameDAL;
import com.danieltalik.fullStackApp.Models.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class NicknameController {
    private IDao nicknameDAL;

    public NicknameController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataAccessConfig.class);
        this.nicknameDAL = context.getBean("nicknameDAL", NicknameDAL.class);
    }

    @GetMapping("/allNicknames")
    public List<Person>getNicknames(){

        return nicknameDAL.getAll();
    }

    @GetMapping("/nickname")
    public Person getNick(@RequestParam String first, @RequestParam String last ){
        return nicknameDAL.getDetails(first,last);
    }

    @GetMapping("/person")
    public Person getPerson(@RequestParam String nickname){

        return nicknameDAL.getName(nickname);

    }
    @PostMapping(path = "/addNickname", consumes = "application/json")
    public void addPerson(@RequestBody Person person) {
        System.out.println(person.getFirstName() + person.getLastName());
        nicknameDAL.insertPerson(person.getFirstName()
                ,person.getLastName()
                ,person.getNickName()
                ,person.getAge()
                ,person.getBirthday());
    }

}
