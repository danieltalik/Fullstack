package com.danieltalik.fullstackapp.repository;

import com.danieltalik.fullstackapp.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Runs against the schema and seed data that Liquibase creates. */
@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void liquibaseSeedsOnePerson() {
        assertThat(repository.findAll()).singleElement().satisfies(person -> {
            assertThat(person.getId()).isEqualTo(1);
            assertThat(person.getNickname()).isEqualTo("Dino");
            assertThat(person.getBirthday()).isEqualTo(LocalDate.of(1988, 9, 20));
        });
    }

    @Test
    void newPeopleGetIdsAfterTheSeedData() {
        // Guards against seeding with an explicit id, which would make this collide with id 1.
        Person saved = repository.saveAndFlush(new Person("Ada", "Lovelace", "Countess", LocalDate.of(1815, 12, 10)));

        assertThat(saved.getId()).isGreaterThan(1);
        assertThat(repository.count()).isEqualTo(2);
    }

    @ParameterizedTest(name = "a null {0} is rejected by the database")
    @ValueSource(strings = {"firstName", "lastName", "nickname", "birthday"})
    void databaseRejectsMissingValues(String field) {
        Person person = new Person(
                field.equals("firstName") ? null : "Ada",
                field.equals("lastName") ? null : "Lovelace",
                field.equals("nickname") ? null : "Countess",
                field.equals("birthday") ? null : LocalDate.of(1815, 12, 10));

        assertThatThrownBy(() -> repository.saveAndFlush(person))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
