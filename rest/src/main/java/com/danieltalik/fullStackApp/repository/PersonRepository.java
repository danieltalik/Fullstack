package com.danieltalik.fullStackApp.repository;

import com.danieltalik.fullStackApp.Models.PersonUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonUpdate,String> {
}
