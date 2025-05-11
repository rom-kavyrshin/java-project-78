package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.function.BiPredicate;

public class StringSchema extends BaseSchema<String> {

    public static final String REQUIRED_TEST_KEY = "required";
    public static final String MIN_LENGTH_TEST_KEY = "minLength";
    public static final String CONTAINS_TEST_KEY = "contains";

    private final BiPredicate<Boolean, String> notNullTest = (ignore, test) -> test != null && !test.isEmpty();
    private final BiPredicate<Integer, String> minLengthTest = (minLength, test) -> test.length() >= minLength;
    private final BiPredicate<String, String> containsStringTest = (contains, test) -> test.contains(contains);

    private final LinkedHashMap<String, ValidationProperty<?, String>> linkedHashMap = new LinkedHashMap<>();

    public StringSchema required() {
        linkedHashMap.putFirst(REQUIRED_TEST_KEY, new ValidationProperty<>(true, notNullTest));
        return this;
    }

    public StringSchema minLength(int minLength) {
        linkedHashMap.put(MIN_LENGTH_TEST_KEY, new ValidationProperty<>(minLength, minLengthTest));
        return this;
    }

    public StringSchema contains(String containsString) {
        linkedHashMap.put(CONTAINS_TEST_KEY, new ValidationProperty<>(containsString, containsStringTest));
        return this;
    }

    @Override
    public boolean isValid(Object object) {
        if (!(object instanceof String) && object != null) {
            return false;
        }

        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test((String) object));
    }
}
