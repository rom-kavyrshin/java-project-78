package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class MapSchema extends BaseSchema {

    private final BiPredicate<Integer, Map<String, Object>> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Map<String, Object>> sizeofTest = (exactSize, test) -> test.size() == exactSize;
    private final BiPredicate<Map<String, BaseSchema>, Map<String, Object>> shapeTest =
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

    public MapSchema shape(Map<String, BaseSchema> schemas) {
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
