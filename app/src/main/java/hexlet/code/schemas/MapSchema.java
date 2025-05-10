package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private final BiPredicate<Integer, Map<String, String>> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Map<String, String>> sizeofTest = (exactSize, test) -> test.size() == exactSize;
    private final BiPredicate<Map<String, BaseSchema<String>>, Map<String, String>> shapeTest =
            (schemas, test) -> {
                return test.entrySet()
                        .stream()
                        .allMatch(it -> {
                            var schemaForObject = schemas.get(it.getKey());
                            if (schemaForObject != null) {
                                return schemaForObject.isValid(it.getValue());
                            } else {
                                return false;
                            }
                        });
            };

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

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        linkedHashMap.put("shape", new ValidationProperty<>(schemas, shapeTest));
        return this;
    }

    @Override
    public boolean isValid(Object object) {
        if (!(object instanceof Map) && object != null) {
            return false;
        }

        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test((Map) object));
    }
}
