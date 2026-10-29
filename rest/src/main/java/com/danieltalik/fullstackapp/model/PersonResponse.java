package com.danieltalik.fullstackapp.model;

import java.time.LocalDate;

/** A person as returned by the API. Age is worked out at read time so it never goes stale. */
public record PersonResponse(
        int id,
        String firstName,
        String lastName,
        String nickname,
        LocalDate birthday,
        int age) {
}
