package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMapSchema {

    MapSchema mapSchema;

    @BeforeEach
    void beforeEach() {
        mapSchema = new Validator().map();
    }

    @Test
    void testRequired() {
        var nonEmptyMap = new HashMap<String, String>();
        nonEmptyMap.put("firstKey", "firstValue");

        assertTrue(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
        assertTrue(mapSchema.isValid(nonEmptyMap));

        mapSchema.required();

        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
        assertTrue(mapSchema.isValid(nonEmptyMap));
    }

    @Test
    void testSizeof() {
        var testMap = new HashMap<String, String>();

        assertTrue(mapSchema.isValid(testMap));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertFalse(mapSchema.isValid(testMap));

        testMap.put("secondKey", "secondValue");
        assertTrue(mapSchema.isValid(testMap));

        testMap.put("thirdKey", "thirdValue");
        assertFalse(mapSchema.isValid(testMap));
    }

    @Test
    void testComplexSchema() {
        var testMap = new HashMap<String, String>();
        assertTrue(mapSchema.isValid(null));

        mapSchema.required();

        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(testMap));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertFalse(mapSchema.isValid(testMap));
        testMap.put("secondKey", "secondValue");
        assertTrue(mapSchema.isValid(testMap));

        assertFalse(mapSchema.isValid(null));
        assertFalse(mapSchema.isValid(new HashMap<>()));
        assertTrue(mapSchema.isValid(testMap));
    }

    @Test
    void requiredLatestTest() {
        var testMap = new HashMap<String, String>();
        testMap.put("firstKey", "firstValue");

        mapSchema.sizeof(1).required();

        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(testMap));
    }

    @Test
    void callValidatorsSeveralTimeTest() {
        var testMap = new HashMap<String, String>();
        mapSchema.required().sizeof(1);

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        assertTrue(mapSchema.isValid(testMap));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("secondKey", "secondValue");
        assertTrue(mapSchema.isValid(testMap));
    }
}
