package com.danieltalik.fullstackapp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

/** Rendered by Spring as a 404 problem detail. */
public class PersonNotFoundException extends ErrorResponseException {

    public PersonNotFoundException(int id) {
        super(HttpStatus.NOT_FOUND, ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                "No person with id " + id), null);
    }
}
