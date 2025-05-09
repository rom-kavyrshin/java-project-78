package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMapSchema {

    MapSchema schema;

    @BeforeEach
    void beforeEach() {
        schema = new Validator().map();
    }

    @Test
    void testRequired() {
        var nonEmptyMap = new HashMap<String, String>();
        nonEmptyMap.put("firstKey", "firstValue");

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(nonEmptyMap));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(nonEmptyMap));
    }

    @Test
    void testSizeof() {
        var testMap = new HashMap<String, String>();

        assertTrue(schema.isValid(testMap));

        schema.sizeof(2);

        assertFalse(schema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertFalse(schema.isValid(testMap));

        testMap.put("secondKey", "secondValue");
        assertTrue(schema.isValid(testMap));

        testMap.put("thirdKey", "thirdValue");
        assertFalse(schema.isValid(testMap));
    }

    @Test
    void testComplexSchema() {
        var testMap = new HashMap<String, String>();
        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(testMap));

        schema.sizeof(2);

        assertFalse(schema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertFalse(schema.isValid(testMap));
        testMap.put("secondKey", "secondValue");
        assertTrue(schema.isValid(testMap));

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(testMap));
    }

    @Test
    void requiredLatestTest() {
        var testMap = new HashMap<String, String>();
        testMap.put("firstKey", "firstValue");

        schema.sizeof(1).required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(testMap));
    }

    @Test
    void callValidatorsSeveralTimeTest() {
        var testMap = new HashMap<String, String>();
        schema.required().sizeof(1);

        assertFalse(schema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertTrue(schema.isValid(testMap));

        schema.sizeof(2);

        assertFalse(schema.isValid(testMap));
        testMap.put("secondKey", "secondValue");
        assertTrue(schema.isValid(testMap));
    }
}
