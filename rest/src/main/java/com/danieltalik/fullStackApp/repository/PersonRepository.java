package com.danieltalik.fullStackApp.repository;

import com.danieltalik.fullStackApp.Models.PersonUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<PersonUpdate,Integer> {
}
