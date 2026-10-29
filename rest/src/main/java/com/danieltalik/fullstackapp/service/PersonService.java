package com.danieltalik.fullstackapp.service;

import com.danieltalik.fullstackapp.model.Person;
import com.danieltalik.fullstackapp.model.PersonRequest;
import com.danieltalik.fullstackapp.model.PersonResponse;
import com.danieltalik.fullstackapp.repository.PersonRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository repository;
    private final Clock clock;

    public PersonService(PersonRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public PersonResponse addPerson(PersonRequest request) {
        Person saved = repository.save(new Person(
                request.firstName().strip(),
                request.lastName().strip(),
                request.nickname().strip(),
                request.birthday()));
        return toResponse(saved);
    }

    public List<PersonResponse> getAllPeople() {
        return repository.findAll(Sort.by("id")).stream().map(this::toResponse).toList();
    }

    public PersonResponse getById(int id) {
        return repository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    int ageOf(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now(clock)).getYears();
    }

    private PersonResponse toResponse(Person person) {
        return new PersonResponse(person.getId(), person.getFirstName(), person.getLastName(),
                person.getNickname(), person.getBirthday(), ageOf(person.getBirthday()));
    }
}
