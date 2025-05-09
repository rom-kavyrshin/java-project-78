package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class MapSchema extends BaseSchema<Map<String, String>> {

    private final BiPredicate<Integer, Map<String, String>> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Map<String, String>> sizeofTest = (exactSize, test) -> test.size() == exactSize;

    private final LinkedHashMap<String, ValidationProperty<?, Map<String, String>>> linkedHashMap =
            new LinkedHashMap<>();

    public MapSchema required() {
        linkedHashMap.putFirst("required", new ValidationProperty<>(1, notNullTest));
        return this;
    }

    public MapSchema sizeof(int size) {
        linkedHashMap.put("sizeof", new ValidationProperty<>(size, sizeofTest));
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        return this;
    }

    @Override
    public boolean isValid(Map<String, String> stringStringMap) {
        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test(stringStringMap));
    }
}
