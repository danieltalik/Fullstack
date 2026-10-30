package com.danieltalik.fullstackapp.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PersonRequestValidationTest {

    private static final LocalDate BIRTHDAY = LocalDate.of(1988, 9, 20);

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        factory.close();
    }

    private static PersonRequest valid() {
        return new PersonRequest("Daniel", "Talik", "Dino", BIRTHDAY);
    }

    private static PersonRequest withField(String field, String value) {
        return switch (field) {
            case "firstName" -> new PersonRequest(value, "Talik", "Dino", BIRTHDAY);
            case "lastName" -> new PersonRequest("Daniel", value, "Dino", BIRTHDAY);
            case "nickname" -> new PersonRequest("Daniel", "Talik", value, BIRTHDAY);
            default -> throw new IllegalArgumentException(field);
        };
    }

    private static Set<String> invalidFields(PersonRequest request) {
        return validator.validate(request).stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Object::toString)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Test
    void validRequestHasNoViolations() {
        assertThat(validator.validate(valid())).isEmpty();
    }

    /** Every text field rejects null, empty and whitespace-only values. */
    static Stream<Arguments> blankValuesForEachField() {
        String[] blanks = {null, "", " ", "   ", "\t", "\n"};
        return Stream.of("firstName", "lastName", "nickname")
                .flatMap(field -> Stream.of(blanks).map(blank -> Arguments.of(field, blank)));
    }

    @ParameterizedTest(name = "{0} = [{1}] is rejected")
    @MethodSource("blankValuesForEachField")
    void rejectsBlankText(String field, String blank) {
        assertThat(invalidFields(withField(field, blank))).containsExactly(field);
    }

    @ParameterizedTest(name = "{0} with {1} characters valid={2}")
    @CsvSource({
            "firstName, 1,   true",
            "firstName, 250, true",
            "firstName, 251, false",
            "lastName,  250, true",
            "lastName,  251, false",
            "nickname,  250, true",
            "nickname,  251, false"
    })
    void enforcesMaxLengthOf250(String field, int length, boolean valid) {
        Set<String> invalid = invalidFields(withField(field, "a".repeat(length)));

        if (valid) {
            assertThat(invalid).isEmpty();
        } else {
            assertThat(invalid).containsExactly(field);
        }
    }

    @ParameterizedTest(name = "birthday {0} days from today valid={1}")
    @CsvSource({
            "-36500, true",
            "-1,     true",
            "0,      true",
            "1,      false",
            "365,    false"
    })
    void birthdayCannotBeInTheFuture(int daysFromToday, boolean valid) {
        PersonRequest request = new PersonRequest("Daniel", "Talik", "Dino", LocalDate.now().plusDays(daysFromToday));

        assertThat(invalidFields(request).isEmpty()).isEqualTo(valid);
    }

    @Test
    void birthdayIsRequired() {
        assertThat(invalidFields(new PersonRequest("Daniel", "Talik", "Dino", null))).containsExactly("birthday");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Zoë", "O'Brien", "Jean-Luc", "李", "😀"})
    void acceptsNonAsciiAndPunctuation(String name) {
        assertThat(invalidFields(withField("firstName", name))).isEmpty();
    }
}
