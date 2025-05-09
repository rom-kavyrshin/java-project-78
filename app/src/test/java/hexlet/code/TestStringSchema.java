package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestStringSchema {

    StringSchema schema;

    @BeforeEach
    void beforeEach() {
        schema = new Validator().string();
    }

    @Test
    void testRequired() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("something"));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("something"));
    }

    @Test
    void testMinLength() {
        assertTrue(schema.isValid("ab"));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid("abcd"));

        schema.minLength(3);

        assertFalse(schema.isValid("ab"));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid("abcd"));
    }

    @Test
    void testContains() {
        assertTrue(schema.isValid("thing"));
        assertTrue(schema.isValid("smething"));
        assertTrue(schema.isValid("something"));

        schema.contains("some");

        assertFalse(schema.isValid("thing"));
        assertFalse(schema.isValid("smething"));
        assertTrue(schema.isValid("something"));
    }

    @Test
    void testComplexSchema() {
        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid("some"));

        schema.minLength(5);

        assertFalse(schema.isValid("some"));
        assertTrue(schema.isValid("thing"));

        schema.contains("some");

        assertFalse(schema.isValid("thing"));

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("some"));
        assertFalse(schema.isValid("thing"));
        assertTrue(schema.isValid("something"));
    }

    @Test
    void requiredLatestTest() {
        schema.minLength(3).contains("some").required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
    }

    @Test
    void callValidatorsSeveralTimeTest() {
        schema.required();

        schema.contains("some").minLength(3);
        assertTrue(schema.isValid("some"));

        schema.contains("thing").minLength(5);
        assertFalse(schema.isValid("some"));
    }
}
