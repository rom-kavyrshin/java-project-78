package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.function.BiPredicate;

public class StringSchema extends BaseSchema {

    private final BiPredicate<Boolean, String> notNullTest = (ignore, test) -> test != null && !test.isEmpty();
    private final BiPredicate<Integer, String> minLengthTest = (minLength, test) -> test.length() >= minLength;
    private final BiPredicate<String, String> containsStringTest = (contains, test) -> test.contains(contains);

    private final LinkedHashMap<String, ValidationProperty<?, String>> linkedHashMap = new LinkedHashMap<>();

    public StringSchema required() {
        linkedHashMap.putFirst("required", new ValidationProperty<>(true, notNullTest));
        return this;
    }

    public StringSchema minLength(int minLength) {
        linkedHashMap.put("minLength", new ValidationProperty<>(minLength, minLengthTest));
        return this;
    }

    public StringSchema contains(String containsString) {
        linkedHashMap.put("contains", new ValidationProperty<>(containsString, containsStringTest));
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
