package com.danieltalik.fullstackapp.controller;

import com.danieltalik.fullstackapp.model.PersonRequest;
import com.danieltalik.fullstackapp.model.PersonResponse;
import com.danieltalik.fullstackapp.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonResponse> getAllPeople() {
        return service.getAllPeople();
    }

    @GetMapping("/{id}")
    public PersonResponse getPerson(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<PersonResponse> addPerson(@Valid @RequestBody PersonRequest request) {
        PersonResponse created = service.addPerson(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }
}
