package hexlet.code.schemas;

import hexlet.code.Pair;

import java.util.LinkedHashMap;
import java.util.function.BiPredicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public static final String REQUIRED_TEST_KEY = "required";
    public static final String POSITIVE_TEST_KEY = "positive";
    public static final String RANGE_TEST_KEY = "range";

    private final BiPredicate<Integer, Integer> notNullTest = (ignore, test) -> test != null;
    private final BiPredicate<Integer, Integer> positiveTest = (ignore, test) -> test > 0;
    private final BiPredicate<Pair<Integer, Integer>, Integer> inRangeTest =
            (contains, test) -> contains.first() <= test && test <= contains.second();

    private final LinkedHashMap<String, ValidationProperty<?, Integer>> linkedHashMap = new LinkedHashMap<>();

    public NumberSchema required() {
        linkedHashMap.putFirst(REQUIRED_TEST_KEY, new ValidationProperty<>(1, notNullTest));
        return this;
    }

    public NumberSchema positive() {
        linkedHashMap.put(POSITIVE_TEST_KEY, new ValidationProperty<>(1, positiveTest));
        return this;
    }

    public NumberSchema range(int start, int end) {
        linkedHashMap.put(RANGE_TEST_KEY, new ValidationProperty<>(Pair.of(start, end), inRangeTest));
        return this;
    }

    @Override
    public boolean isValid(Object object) {
        if (object == null && !linkedHashMap.containsKey(REQUIRED_TEST_KEY)) {
            return true;
        }
        if (!(object instanceof Integer) && object != null) {
            return false;
        }

        return linkedHashMap.values()
                .stream()
                .allMatch(it -> it.test((Integer) object));
    }
}
