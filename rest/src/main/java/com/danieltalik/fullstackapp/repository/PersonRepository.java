package com.danieltalik.fullstackapp.repository;

import com.danieltalik.fullstackapp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
