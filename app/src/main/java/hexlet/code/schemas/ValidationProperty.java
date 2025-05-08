package hexlet.code.schemas;

import java.util.function.BiPredicate;

public class ValidationProperty<T, U> {

    private final T property;
    private final BiPredicate<T, U> callback;

    public ValidationProperty(T property, BiPredicate<T, U> callback) {
        this.property = property;
        this.callback = callback;
    }

    public boolean test(U u) {
        return callback.test(property, u);
    }
}
