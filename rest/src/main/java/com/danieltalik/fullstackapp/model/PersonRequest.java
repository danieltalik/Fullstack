package com.danieltalik.fullstackapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** Body of POST /api/people. */
public record PersonRequest(
        @NotBlank @Size(max = 250) String firstName,
        @NotBlank @Size(max = 250) String lastName,
        @NotBlank @Size(max = 250) String nickname,
        @NotNull @PastOrPresent LocalDate birthday) {
}
