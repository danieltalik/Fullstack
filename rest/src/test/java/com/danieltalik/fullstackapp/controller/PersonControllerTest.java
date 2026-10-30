package com.danieltalik.fullstackapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Full-stack tests through MockMvc. Each test runs in a transaction that is rolled back,
 * so every test starts with just the seeded person.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String json(String firstName, String lastName, String nickname, String birthday) {
        return """
                {"firstName":%s,"lastName":%s,"nickname":%s,"birthday":%s}
                """.formatted(quote(firstName), quote(lastName), quote(nickname), quote(birthday));
    }

    private static String quote(String value) {
        return value == null ? "null" : "\"" + value + "\"";
    }

    @Test
    void listsSeededPerson() throws Exception {
        mockMvc.perform(get("/api/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nickname").value("Dino"))
                .andExpect(jsonPath("$[0].birthday").value("1988-09-20"))
                .andExpect(jsonPath("$[0].age").isNumber());
    }

    @ParameterizedTest(name = "GET /api/people/{0} -> 404")
    @ValueSource(ints = {0, -1, 2, 999, Integer.MAX_VALUE})
    void unknownIdsReturn404(int id) throws Exception {
        mockMvc.perform(get("/api/people/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("No person with id " + id));
    }

    @ParameterizedTest(name = "GET /api/people/{0} -> 400")
    @ValueSource(strings = {"abc", "1.5", "2147483648", "one"})
    void nonNumericIdsReturn400(String id) throws Exception {
        mockMvc.perform(get("/api/people/{id}", id)).andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "adds {2} ({0} {1})")
    @CsvSource({
            "Ada,      Lovelace, Countess, 1815-12-10",
            "Grace,    Hopper,   Amazing,  1906-12-09",
            "Zoë,      O'Brien,  Zo,       2000-02-29"
    })
    void addsPersonAndReturnsLocation(String first, String last, String nickname, String birthday) throws Exception {
        String location = mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON)
                        .content(json(first, last, nickname, birthday)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("http://localhost/api/people/\\d+")))
                .andExpect(jsonPath("$.firstName").value(first))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.birthday").value(birthday))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value(last));
        mockMvc.perform(get("/api/people")).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void trimsWhitespace() throws Exception {
        mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON)
                        .content(json("  Ada ", " Lovelace", "Countess  ", "1815-12-10")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Ada"))
                .andExpect(jsonPath("$.lastName").value("Lovelace"))
                .andExpect(jsonPath("$.nickname").value("Countess"));
    }

    static Stream<Arguments> invalidBodies() {
        String tomorrow = LocalDate.now().plusDays(1).toString();
        return Stream.of(
                Arguments.of("missing first name", json(null, "B", "C", "2000-01-01")),
                Arguments.of("blank last name", json("A", "  ", "C", "2000-01-01")),
                Arguments.of("empty nickname", json("A", "B", "", "2000-01-01")),
                Arguments.of("missing birthday", json("A", "B", "C", null)),
                Arguments.of("birthday tomorrow", json("A", "B", "C", tomorrow)),
                Arguments.of("birthday not a date", json("A", "B", "C", "yesterday")),
                Arguments.of("impossible date", json("A", "B", "C", "2001-02-29")),
                Arguments.of("US date format", json("A", "B", "C", "09/20/1988")),
                Arguments.of("name too long", json("a".repeat(251), "B", "C", "2000-01-01")),
                Arguments.of("old field name nickName", """
                        {"firstName":"A","lastName":"B","nickName":"C","birthday":"2000-01-01"}
                        """),
                Arguments.of("empty object", "{}"),
                Arguments.of("malformed JSON", "{\"firstName\":"),
                Arguments.of("array", "[]"));
    }

    @ParameterizedTest(name = "[{index}] {0} -> 400")
    @MethodSource("invalidBodies")
    void rejectsInvalidBodies(String description, String body) throws Exception {
        mockMvc.perform(post("/api/people").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/people")).andExpect(jsonPath("$", hasSize(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    void rejectsNonJson(String contentType) throws Exception {
        mockMvc.perform(post("/api/people").contentType(contentType)
                        .content(json("A", "B", "C", "2000-01-01")))
                .andExpect(status().isUnsupportedMediaType());
    }

    @ParameterizedTest(name = "{0} /api/people -> 405")
    @ValueSource(strings = {"PUT", "DELETE", "PATCH"})
    void unsupportedMethodsReturn405(String method) throws Exception {
        mockMvc.perform(request(org.springframework.http.HttpMethod.valueOf(method), "/api/people"))
                .andExpect(status().isMethodNotAllowed());
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "/actuator/health, 200",
            "/actuator/info,   200",
            "/actuator/env,    404",
            "/h2-console,      404"
    })
    void onlySafeOperationalEndpointsAreExposed(String path, int expectedStatus) throws Exception {
        mockMvc.perform(get(path)).andExpect(status().is(expectedStatus));
    }
}
