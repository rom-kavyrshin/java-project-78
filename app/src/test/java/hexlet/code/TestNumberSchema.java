package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNumberSchema {

    private NumberSchema schema;

    @BeforeEach
    void beforeEach() {
        schema = new Validator().number();
    }

    @Test
    void testRequired() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(1));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(1));
    }

    @Test
    void testPositive() {
        assertTrue(schema.isValid(-1));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(1));

        schema.positive();

        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(1));
    }

    @Test
    void testRange() {
        assertTrue(schema.isValid(4));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(6));
        assertTrue(schema.isValid(9));
        assertTrue(schema.isValid(10));
        assertTrue(schema.isValid(11));

        schema.range(5, 10);

        assertFalse(schema.isValid(4));
        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(6));
        assertTrue(schema.isValid(9));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testComplexSchema() {
        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(-1));

        schema.positive();

        assertFalse(schema.isValid(-1));
        assertTrue(schema.isValid(1));

        schema.range(5, 10);

        assertFalse(schema.isValid(1));

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(-1));
        assertFalse(schema.isValid(1));
        assertTrue(schema.isValid(7));
    }

    @Test
    void requiredLatestTest() {
        schema.positive().range(5, 10).required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(6));
    }

    @Test
    void testNullWithoutRequired() {
        schema.positive().range(5, 10);

        assertTrue(schema.isValid(null));
    }

    @Test
    void callValidatorsSeveralTimeTest() {
        schema.required();

        schema.range(5, 10).positive();
        assertTrue(schema.isValid(7));

        schema.range(20, 25).positive();
        assertFalse(schema.isValid(7));
        assertTrue(schema.isValid(22));
    }

    @Test
    void testWrongType() {
        schema.required();

        schema.range(5, 10).positive();
        assertFalse(schema.isValid("something"));
        assertTrue(schema.isValid(7));
    }
}
