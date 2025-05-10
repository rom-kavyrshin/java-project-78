package hexlet.code.schemas;

import hexlet.code.Pair;

import java.util.LinkedHashMap;
import java.util.function.BiPredicate;

public class NumberSchema extends BaseSchema {

    private final BiPredicate<Integer, Integer> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Integer> positiveTest = (minLength, test) -> test > 0;
    private final BiPredicate<Pair<Integer, Integer>, Integer> inRangeTest =
            (contains, test) -> contains.first() <= test && test <= contains.second();

    private final LinkedHashMap<String, ValidationProperty<?, Integer>> linkedHashMap = new LinkedHashMap<>();

    public NumberSchema required() {
        linkedHashMap.putFirst("required", new ValidationProperty<>(1, notNullTest));
        return this;
    }

    public NumberSchema positive() {
        linkedHashMap.put("positive", new ValidationProperty<>(1, positiveTest));
        return this;
    }

    public NumberSchema range(int start, int end) {
        linkedHashMap.put("range", new ValidationProperty<>(Pair.of(start, end), inRangeTest));
        return this;
    }

    @Override
    public boolean isValid(Object object) {
        if (!(object instanceof Integer) && object != null) {
            return false;
        }

        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test((Integer) object));
    }
}
