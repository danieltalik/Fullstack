package com.danieltalik.fullStackApp.service;

import com.danieltalik.fullStackApp.Models.PersonUpdate;
import com.danieltalik.fullStackApp.Models.PersonRequest;
import com.danieltalik.fullStackApp.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository repo;

    public PersonService(PersonRepository repo){
        this.repo = repo;
    }

    public PersonUpdate addPerson(PersonRequest request){
        PersonUpdate.PersonUpdateBuilder h2Person = PersonUpdate.builder();
        h2Person.firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nickname(request.getNickName())
                .birthday(Date.from(request.getBirthday().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .age(LocalDateTime.now().getYear() - request.getBirthday().getYear());
        return repo.save(h2Person.build());
    }
    public Iterable<PersonUpdate> getAllPeople(){
        return repo.findAll();
    }

    public Optional<PersonUpdate> getById(int id){return repo.findById(id);}
}
