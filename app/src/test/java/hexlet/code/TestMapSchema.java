package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestMapSchema {

    Validator validator;
    MapSchema mapSchema;

    @BeforeEach
    void beforeEach() {
        validator = new Validator();
        mapSchema = validator.map();
    }

    @Test
    void testRequired() {
        var nonEmptyMap = new HashMap<String, Object>();
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
        var testMap = new HashMap<String, Object>();

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
    void testShapeWrongKeys() {
        var schemas = new HashMap<String, BaseSchema<?>>();
        var testMap = new HashMap<String, Object>();

        assertTrue(mapSchema.isValid(testMap));

        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        mapSchema.required().sizeof(2).shape(schemas);

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("firstKey", "firstValue");
        testMap.put("secondKey", "secondValue");
        assertFalse(mapSchema.isValid(testMap));

        testMap.clear();

        assertFalse(mapSchema.isValid(testMap));
        testMap.put("firstName", "John");
        testMap.put("lastName", "Lenon");

        assertTrue(mapSchema.isValid(testMap));
    }

    @Test
    void testShapeExtended() {
        var schemas = new HashMap<String, BaseSchema<?>>();
        var testMap = new HashMap<String, Object>();

        assertTrue(mapSchema.isValid(testMap));

        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));
        schemas.put("age", validator.number().required().range(21, 30));

        mapSchema.required().sizeof(3).shape(schemas);

        testMap.put("firstName", null);
        testMap.put("lastName", "Lenon");
        testMap.put("age", 25);
        assertFalse(mapSchema.isValid(testMap));

        testMap.put("firstName", "John");
        assertTrue(mapSchema.isValid(testMap));

        testMap.put("lastName", "L");
        assertFalse(mapSchema.isValid(testMap));
        testMap.put("lastName", "Lenon");
        assertTrue(mapSchema.isValid(testMap));

        testMap.put("age", 31);
        assertFalse(mapSchema.isValid(testMap));
        testMap.put("age", 25);
        assertTrue(mapSchema.isValid(testMap));
    }

    @Test
    void testComplexSchema() {
        var testMap = new HashMap<String, Object>();
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
        var testMap = new HashMap<String, Object>();
        testMap.put("firstKey", "firstValue");

        mapSchema.sizeof(1).required();

        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(testMap));
    }

    @Test
    void callValidatorsSeveralTimeTest() {
        var testMap = new HashMap<String, Object>();
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
