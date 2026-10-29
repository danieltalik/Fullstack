package com.danieltalik.fullstackapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsSeededPerson() throws Exception {
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nickname").value("Dino"))
                .andExpect(jsonPath("$[0].birthday").value("1988-09-20"));
    }

    @Test
    void returns404ForUnknownPerson() throws Exception {
        mockMvc.perform(get("/api/people/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("No person with id 999"));
    }

    @Test
    void addsPerson() throws Exception {
        mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ada","lastName":"Lovelace","nickname":"Countess","birthday":"1815-12-10"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/people/2")))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nickname").value("Countess"));

        mockMvc.perform(get("/api/people/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ada"));
    }

    @Test
    void rejectsMissingFields() throws Exception {
        mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ada","lastName":"","nickname":"Countess"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsFutureBirthday() throws Exception {
        mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"A","lastName":"B","nickname":"C","birthday":"2999-01-01"}
                                """))
                .andExpect(status().isBadRequest());
    }
}
