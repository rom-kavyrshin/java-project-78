package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, String>> {

    public MapSchema required() {
        return this;
    }

    public MapSchema sizeof(int size) {
        return this;
    }

    @Override
    public boolean isValid(Map<String, String> stringStringMap) {
        return false;
    }
}
