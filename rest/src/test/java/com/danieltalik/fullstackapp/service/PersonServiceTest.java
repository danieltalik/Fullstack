package com.danieltalik.fullstackapp.service;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class PersonServiceTest {

    private final Clock clock = Clock.fixed(Instant.parse("2026-09-20T12:00:00Z"), ZoneOffset.UTC);
    private final PersonService service = new PersonService(null, clock);

    @Test
    void ageIncreasesOnTheBirthday() {
        assertThat(service.ageOf(LocalDate.of(1988, 9, 20))).isEqualTo(38);
        assertThat(service.ageOf(LocalDate.of(1988, 9, 21))).isEqualTo(37);
    }

    @Test
    void newbornIsZero() {
        assertThat(service.ageOf(LocalDate.of(2026, 9, 20))).isZero();
    }
}
