package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private final BiPredicate<Integer, Map<String, Object>> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Map<String, Object>> sizeofTest = (exactSize, test) -> test.size() == exactSize;

    private final LinkedHashMap<String, ValidationProperty<?, Map<String, Object>>> linkedHashMap =
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
    public boolean isValid(Map<String, Object> stringStringMap) {
        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test(stringStringMap));
    }
}
